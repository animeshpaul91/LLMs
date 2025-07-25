import os

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI, Stream
from openai.types.chat import ChatCompletionChunk
from pydantic import BaseModel

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")


USER, BOT = "user", "bot"
class ChatMessage(BaseModel):
    sender: str
    content: str
    pass


def set_streamlit_config():
    st.set_page_config(page_title="Chat Application")
    st.header(":blue[Chat Application]")
    if "chat_history" not in st.session_state:
        st.session_state["chat_history"] = [ChatMessage(sender=BOT, content="Hello, how may I help you today?")]
    chat_history = st.session_state["chat_history"]
    for chat_message in chat_history:
        if chat_message.sender == BOT:
            st.chat_message("ai").write(chat_message.content)
        if chat_message.sender == USER:
            st.chat_message("user").write(chat_message.content)


# Call the openai chat.completions endpoint
def ask_openai(user_question: str, temperature: float = 1.0, top_p: float = 1.0, max_tokens: int = 256) -> Stream[
    ChatCompletionChunk]:
    print("\nStarting LLM call")
    response = client.chat.completions.create(model=LLM,
                                              messages=[{"role": "user", "content": user_question}],
                                              temperature=temperature,
                                              max_tokens=max_tokens,
                                              top_p=top_p,
                                              stream=True
                                              )

    return response


def response_generator(user_question: str) -> str:
    response_stream: Stream[ChatCompletionChunk] = ask_openai(user_question=user_question)
    print("Fetched LLM response\n")
    for chunk in response_stream:
        if chunk.choices and chunk.choices[0].delta.content:
            chunk_message = chunk.choices[0].delta.content
            yield chunk_message


def run() -> None:
    # Set Streamlit Web-Page Layout
    set_streamlit_config()

    prompt = st.chat_input("Ask anything")

    if prompt:
        st.chat_message("user").write(prompt)
        st.session_state["chat_history"].append(ChatMessage(sender=USER, content=prompt))
        output = response_generator(prompt)

        with st.chat_message("ai"):
            ai_message = st.write_stream(output)

        st.session_state["chat_history"].append(ChatMessage(sender=BOT, content=ai_message))


if __name__ == "__main__":
    run()
