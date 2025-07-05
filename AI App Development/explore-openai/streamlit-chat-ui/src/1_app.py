import os

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI, Stream
from openai.types.chat import ChatCompletionChunk

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")


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


def set_streamlit_config():
    st.set_page_config(page_title="Chat Application")
    st.header(":blue[Chat Application]")
    st.chat_message("ai").write("Hello, how can I help you today?")


def response_generator(user_question: str) -> str:
    response_stream: Stream[ChatCompletionChunk] = ask_openai(user_question=user_question)
    print("Fetched LLM response\n")
    for chunk in response_stream:
        if chunk.choices and chunk.choices[0].delta.content:
            chunk_message = chunk.choices[0].delta.content
            yield chunk_message


def run() -> None:
    prompt = st.chat_input("Add your prompt..")

    if prompt:
        st.chat_message("human").write(prompt)
        st.chat_message("ai").write_stream(response_generator(prompt))


if __name__ == "__main__":
    set_streamlit_config()
    run()
