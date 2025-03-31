import json
import os

from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

# Set up load_dotenv()
load_dotenv() # sets up env variables from .env

# Call open-ai model using chat completion create.
client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")

def ask_openai(input_prompt: str) -> ChatCompletion:
    response = client.chat.completions.create(
        model=LLM,
        messages=[
            {
                "role": "user",
                "content": input_prompt
            }
        ],
    )
    return response

if __name__ == "__main__":
    # Print the Type and Response
    user_question = "Generate the code for the sum of n numbers"
    llm_response = ask_openai(user_question)
    print(f"Response type : {type(llm_response)}")

    # Pretty print the entire response
    response_dict = llm_response.to_dict()
    print(json.dumps(response_dict, indent=4))

    # Print the response text
    print(f"Response : {llm_response.choices[0].message.content}")
