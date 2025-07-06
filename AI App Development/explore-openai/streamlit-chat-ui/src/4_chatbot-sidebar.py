import os
import uuid

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

# Define a model for chat messages
class ChatMessage(BaseModel):
    sender: str
    content: str
    pass

# Configure Streamlit page and sidebar
def set_streamlit_config():
    st.set_page_config(page_title="Chat Application")
    st.header(":blue[Chat Application]")

    # Initialize chat history and current session state
    if "conversations" not in st.session_state:
        st.session_state["conversations"] = {}
    if "active_chat_id" not in st.session_state:
        st.session_state["active_chat_id"] = None
    if "chat_titles" not in st.session_state:
        st.session_state["chat_titles"] = {}

    # Sidebar layout for managing conversations
    with st.sidebar:
        st.title("Conversations")

        # Create a new chat session
        if st.button("+ New Chat"):
            new_chat_id = str(uuid.uuid4())
            st.session_state.conversations[new_chat_id] = [ChatMessage(sender=BOT, content="Hello, how may I help you today?")]
            st.session_state.active_chat_id = new_chat_id
            st.session_state.chat_titles[new_chat_id] = "New Chat"

        # List existing chat sessions
        for chat_id in st.session_state.conversations:
            title = st.session_state.chat_titles.get(chat_id, "Unnamed Chat")
            if st.button(title, key=chat_id):
                st.session_state.active_chat_id = chat_id

    # Display messages of the selected conversation
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
    # Setup Streamlit layout
    set_streamlit_config()

    # Only allow input if a chat is active
    if st.session_state.active_chat_id:
        prompt = st.chat_input("Ask anything")

        if prompt:
            # Display and store user message
            st.chat_message("user").write(prompt)
            st.session_state.conversations[st.session_state.active_chat_id].append(ChatMessage(sender=USER, content=prompt))

            # Generate and stream response from OpenAI
            output = response_generator(prompt)
            with st.chat_message("ai"):
                ai_message = st.write_stream(output)

            # Store response message
            st.session_state.conversations[st.session_state.active_chat_id].append(ChatMessage(sender=BOT, content=ai_message))

            # Set chat title only if it's still "New Chat"
            current_title = st.session_state.chat_titles.get(st.session_state.active_chat_id, "")
            if current_title == "New Chat":
                st.session_state.chat_titles[st.session_state.active_chat_id] = prompt[:50]  # Limit title length

            print(f"Logging all chats\n {st.session_state['conversations']}")


# Run the application
if __name__ == "__main__":
    run()
