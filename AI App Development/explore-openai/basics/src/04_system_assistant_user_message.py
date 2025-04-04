import json
import os

from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")


# Call the openai chat.completions endpoint
def ask_openai(
    user_question: str,
    temperature: float = 1.0,
    top_p: float = 1.0,
    max_tokens: int = 256,
) -> ChatCompletion:
    print(f"LLM : {LLM}")

    system_message = """
        You are a helpful assistant that answers questions about Python programming!
        If the user asks any other questions then you can respond with I don't know in a funny way!
    """

    response = client.chat.completions.create(
        model=LLM,
        messages=[
            {"role": "system", "content": system_message},
            {"role": "user", "content": "My name is Animesh\n"},
            {"role": "user", "content": user_question},
        ],
        temperature=temperature,
        max_tokens=max_tokens,
        top_p=top_p,
    )

    return response


if __name__ == "__main__":
    # user_prompt = "How do I create a Thread in Python ?"
    user_prompt = "What is my name?"
    llm_response: ChatCompletion = ask_openai(user_prompt)

    # Pretty print the entire response
    response_dict = llm_response.to_dict()
    print(json.dumps(response_dict, indent=4))

    string_response = llm_response.choices[0].message.content
    print(f"response : {string_response}")
