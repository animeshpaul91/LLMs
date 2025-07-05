import os

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")


# Call the openai chat.completions endpoint
def ask_openai(user_question: str, temperature: float = 1.0, top_p: float = 1.0, max_tokens: int = 256, stream=False) -> str:
    print("\nStarting LLM call")
    response = client.chat.completions.create(model=LLM,
        messages=[{"role": "user", "content": user_question}],
        temperature=temperature,
        max_tokens=max_tokens,
        top_p=top_p,
        #stream=stream
    )

    llm_response = response.choices[0].message.content
    print(f"Fetched LLM response: \n{llm_response}")
    return llm_response

def set_streamlit_config():
    st.set_page_config(page_title="Chat Application")
    st.header("Chat :blue[Application]")
    st.chat_message("ai").write("Hello, how can I help you today?")


def run() -> None:
    prompt = st.chat_input("Add your prompt..")

    if prompt:
        st.chat_message("human").write(prompt)
        llm_output: str = ask_openai(prompt)
        st.chat_message("ai").write(llm_output)

if __name__ == "__main__":
    set_streamlit_config()
    run()