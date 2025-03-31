import os
import json
from openai import OpenAI
from dotenv import load_dotenv


if __name__ == "__main__":
    # Set up load_dotenv()
    load_dotenv() # sets up env variables from .env

    # Call open-ai model using chat completion create.
    client = OpenAI()
    LLM = os.environ.get("OPEN_AI_MODEL")

    response = client.chat.completions.create(
        model=LLM,
        messages=[
            {
                "role": "user",
                "content": "What is an LLM ?"
            }
        ],
    )

    # Print the Type and Response
    print(f"Response type : {type(response)}")

    # Pretty print the entire response
    # print(f"Response : {response.choices[0].message.content}")
    response_dict = response.to_dict()
    print(json.dumps(response_dict, indent=4))