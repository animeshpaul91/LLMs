import os

from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")


# Call the openai chat.completions endpoint
def ask_openai(user_question: str, temperature: float = 1.0) -> ChatCompletion:
    response = client.chat.completions.create(
        model=LLM,
        messages=[{"role": "user", "content": user_question}],
        temperature=temperature
    )

    # print(f"response  type : {type(response)}")
    return response


def get_response(user_question: str, temperature: float = 0.2) -> str:
    response = ask_openai(user_question, temperature)
    return response.choices[0].message.content


if __name__ == "__main__":
    # temperature
    # 0.2 -> Deterministic
    # 1 and above  -> non deterministic
    input_prompt = """
        Can you complete the sentence ?

        My dog is playful and love to
        """

    # top_p
    # Start with 1, -> run around in the park, chasing after balls and playing with other dogs.
    #  0.5-> My dog is playful and loves to chase after balls in the park.
    #  0.2-> My dog is playful and loves to chase after balls in the park.

    response_text: str = get_response(input_prompt, temperature=1.0)
    print(f"response_temp_01 : {response_text}")
