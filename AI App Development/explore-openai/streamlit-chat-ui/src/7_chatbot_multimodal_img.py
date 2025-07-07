import os
import uuid
import sqlite3
import base64
import requests
import tempfile

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI, Stream
from openai.types.chat import ChatCompletionChunk
from pydantic import BaseModel

# Load environment variables from .env file
load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")
api_key = os.environ.get("OPENAI_API_KEY")

USER, BOT = "user", "bot"
DB_NAME = "chatbot.db"


def get_db_connection():
    conn = sqlite3.connect(DB_NAME)
    return conn, conn.cursor()

def init_db():
    conn, cursor = get_db_connection()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS messages (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            chat_id TEXT NOT NULL,
            sender TEXT NOT NULL,
            content TEXT NOT NULL,
            image_path TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS chat_titles (
            chat_id TEXT PRIMARY KEY,
            title TEXT
        )
    ''')
    conn.commit()
    conn.close()

def save_message(chat_id, sender, content, image_path=None):
    conn, cursor = get_db_connection()
    cursor.execute(
        "INSERT INTO messages (chat_id, sender, content, image_path) VALUES (?, ?, ?, ?)",
        (chat_id, sender, content, image_path)
    )
    conn.commit()
    conn.close()

def load_messages(chat_id):
    conn, cursor = get_db_connection()
    cursor.execute("SELECT sender, content, image_path FROM messages WHERE chat_id = ? ORDER BY created_at", (chat_id,))
    rows = cursor.fetchall()
    conn.close()
    return [ChatMessage(sender=row[0], content=row[1], image_path=row[2]) for row in rows]

def update_chat_title(chat_id, title):
    conn, cursor = get_db_connection()
    cursor.execute("REPLACE INTO chat_titles (chat_id, title) VALUES (?, ?)", (chat_id, title))
    conn.commit()
    conn.close()

def load_chat_titles():
    conn, cursor = get_db_connection()
    cursor.execute("SELECT chat_id, title FROM chat_titles")
    rows = cursor.fetchall()
    conn.close()
    return {row[0]: row[1] for row in rows}

class ChatMessage(BaseModel):
    sender: str
    content: str
    image_path: str = None

def encode_image(image_path):
    with open(image_path, "rb") as image_file:
        return base64.b64encode(image_file.read()).decode("utf-8")

def ask_openai(user_question: str, temperature: float = 1.0, top_p: float = 1.0, max_tokens: int = 256):
    response = client.chat.completions.create(
        model=LLM,
        messages=[{"role": "user", "content": user_question}],
        temperature=temperature,
        max_tokens=max_tokens,
        top_p=top_p,
        stream=True
    )
    return response

def ask_openai_image(user_question: str, image_path: str, temperature: float = 1.0, top_p: float = 1.0, max_tokens: int = 500):
    base64_image = encode_image(image_path)
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {api_key}"
    }
    payload = {
        "model": "gpt-4o-mini",
        "messages": [
            {
                "role": "user",
                "content": [
                    {"type": "text", "text": user_question},
                    {"type": "image_url", "image_url": {"url": f"data:image/jpeg;base64,{base64_image}"}}
                ]
            }
        ],
        "max_tokens": max_tokens,
        "temperature": temperature
    }
    response = requests.post("https://api.openai.com/v1/chat/completions", headers=headers, json=payload)
    return response

def response_generator(user_question: str):
    response_stream = ask_openai(user_question=user_question)
    for chunk in response_stream:
        if chunk.choices and chunk.choices[0].delta.content:
            yield chunk.choices[0].delta.content

def run():
    init_db()
    st.set_page_config(page_title="Chat Application")
    st.header(":blue[Chat Application]")

    if "conversations" not in st.session_state:
        st.session_state["conversations"] = {}
    if "active_chat_id" not in st.session_state:
        st.session_state["active_chat_id"] = None
    if "chat_titles" not in st.session_state:
        st.session_state["chat_titles"] = load_chat_titles()

    with st.sidebar:
        st.title("Conversations")
        if st.button("+ New Chat"):
            new_chat_id = str(uuid.uuid4())
            welcome_msg = ChatMessage(sender=BOT, content="Hello, how may I help you today?")
            st.session_state.conversations[new_chat_id] = [welcome_msg]
            st.session_state.active_chat_id = new_chat_id
            st.session_state.chat_titles[new_chat_id] = "New Chat"
            update_chat_title(new_chat_id, "New Chat")
            save_message(new_chat_id, welcome_msg.sender, welcome_msg.content)

        for chat_id in st.session_state.chat_titles:
            title = st.session_state.chat_titles[chat_id]
            if st.button(title, key=chat_id):
                st.session_state.active_chat_id = chat_id
                if chat_id not in st.session_state.conversations:
                    st.session_state.conversations[chat_id] = load_messages(chat_id)

    if st.session_state.active_chat_id:
        chat_history = st.session_state.conversations[st.session_state.active_chat_id]
        for msg in chat_history:
            with st.chat_message("user" if msg.sender == USER else "ai"):
                if msg.image_path:
                    st.image(msg.image_path, caption="Uploaded Image", use_container_width=True)
                st.write(msg.content)

        with st.form("chat_form"):
            prompt = st.text_input("Enter your message")
            uploaded_file = st.file_uploader("Upload an image (optional)", type=["png", "jpg", "jpeg"])
            submitted = st.form_submit_button("Send")

        if submitted and prompt:
            chat_id = st.session_state.active_chat_id
            image_path = None

            if uploaded_file:
                with tempfile.NamedTemporaryFile(delete=False, suffix=".jpg") as tmp_file:
                    tmp_file.write(uploaded_file.read())
                    image_path = tmp_file.name

            st.chat_message("user").write(prompt)
            if image_path:
                st.image(image_path, caption="Uploaded Image", use_container_width=True)
            save_message(chat_id, USER, prompt, image_path)
            st.session_state.conversations[chat_id].append(ChatMessage(sender=USER, content=prompt, image_path=image_path))

            if image_path:
                response = ask_openai_image(prompt, image_path)
                answer = response.json()["choices"][0]["message"]["content"]
            else:
                output = response_generator(prompt)
                with st.chat_message("ai"):
                    answer = st.write_stream(output)

            st.chat_message("ai").write(answer)
            save_message(chat_id, BOT, answer)
            st.session_state.conversations[chat_id].append(ChatMessage(sender=BOT, content=answer))

            if st.session_state.chat_titles[chat_id] == "New Chat":
                new_title = prompt[:50]
                st.session_state.chat_titles[chat_id] = new_title
                update_chat_title(chat_id, new_title)

if __name__ == "__main__":
    run()
