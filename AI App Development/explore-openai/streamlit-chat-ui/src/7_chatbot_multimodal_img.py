import os
import uuid
import sqlite3

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI, Stream
from openai.types.chat import ChatCompletionChunk
from pydantic import BaseModel

# Load environment variables from .env file
load_dotenv()

# Initialize OpenAI client
client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")

# Define constants for sender types
USER, BOT = "user", "bot"

DB_NAME = "chatbot.db"


# Database helpers
def get_db_connection():
    conn = sqlite3.connect(DB_NAME)
    return conn, conn.cursor()


def init_db():
    conn, cursor = get_db_connection()
    cursor.execute('''
                   CREATE TABLE IF NOT EXISTS messages
                   (
                       id
                       INTEGER
                       PRIMARY
                       KEY
                       AUTOINCREMENT,
                       chat_id
                       TEXT
                       NOT
                       NULL,
                       sender
                       TEXT
                       NOT
                       NULL,
                       content
                       TEXT
                       NOT
                       NULL,
                       created_at
                       TIMESTAMP
                       DEFAULT
                       CURRENT_TIMESTAMP
                   )
                   ''')
    cursor.execute('''
                   CREATE TABLE IF NOT EXISTS chat_titles
                   (
                       chat_id
                       TEXT
                       PRIMARY
                       KEY,
                       title
                       TEXT
                   )
                   ''')
    conn.commit()
    conn.close()


def save_message(chat_id, sender, content):
    conn, cursor = get_db_connection()
    cursor.execute(
        "INSERT INTO messages (chat_id, sender, content) VALUES (?, ?, ?)",
        (chat_id, sender, content)
    )
    conn.commit()
    conn.close()


def load_messages(chat_id):
    conn, cursor = get_db_connection()
    cursor.execute("SELECT sender, content FROM messages WHERE chat_id = ? ORDER BY created_at", (chat_id,))
    rows = cursor.fetchall()
    conn.close()
    return [ChatMessage(sender=row[0], content=row[1]) for row in rows]


def upsert_chat_title(chat_id, title):
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


# Define a model for chat messages
class ChatMessage(BaseModel):
    sender: str
    content: str
    pass


# Configure Streamlit page and sidebar
def set_streamlit_config():
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
            upsert_chat_title(new_chat_id, "New Chat")
            save_message(new_chat_id, welcome_msg.sender, welcome_msg.content)

        for chat_id in st.session_state.chat_titles:
            title = st.session_state.chat_titles[chat_id]
            if st.button(title, key=chat_id):
                st.session_state.active_chat_id = chat_id
                if chat_id not in st.session_state.conversations:
                    st.session_state.conversations[chat_id] = load_messages(chat_id)

    if st.session_state.active_chat_id:
        chat_history = st.session_state.conversations[st.session_state.active_chat_id]
        for chat_message in chat_history:
            if chat_message.sender == BOT:
                st.chat_message("ai").write(chat_message.content)
            if chat_message.sender == USER:
                st.chat_message("user").write(chat_message.content)


# Call the OpenAI API to get a response
def ask_openai(user_question: str, temperature: float = 1.0, top_p: float = 1.0, max_tokens: int = 256) -> Stream[
    ChatCompletionChunk]:
    print("\nStarting LLM call")
    response = client.chat.completions.create(
        model=LLM,
        messages=[{"role": "user", "content": user_question}],
        temperature=temperature,
        max_tokens=max_tokens,
        top_p=top_p,
        stream=True
    )
    return response


# Generator to stream OpenAI response
def response_generator(user_question: str) -> str:
    response_stream: Stream[ChatCompletionChunk] = ask_openai(user_question=user_question)
    print("Fetched LLM response\n")
    for chunk in response_stream:
        if chunk.choices and chunk.choices[0].delta.content:
            chunk_message = chunk.choices[0].delta.content
            yield chunk_message


# Main app logic
def run() -> None:
    init_db()
    set_streamlit_config()

    if st.session_state.active_chat_id:
        prompt = st.chat_input("Ask anything")

        if prompt:
            st.chat_message("user").write(prompt)
            user_msg = ChatMessage(sender=USER, content=prompt)
            st.session_state.conversations[st.session_state.active_chat_id].append(user_msg)
            save_message(st.session_state.active_chat_id, USER, prompt)

            output = response_generator(prompt)
            with st.chat_message("ai"):
                ai_message = st.write_stream(output)

            st.session_state.conversations[st.session_state.active_chat_id].append(
                ChatMessage(sender=BOT, content=ai_message))
            save_message(st.session_state.active_chat_id, BOT, ai_message)

            current_title = st.session_state.chat_titles.get(st.session_state.active_chat_id, "")
            if current_title == "New Chat":
                new_title = prompt[:50]
                st.session_state.chat_titles[st.session_state.active_chat_id] = new_title
                upsert_chat_title(st.session_state.active_chat_id, new_title)

            print(f"Logging all chats\n {st.session_state['conversations']}")


if __name__ == "__main__":
    run()
