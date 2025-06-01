import os
from datetime import datetime

from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")

system_message = """You are an intelligent assistant capable of performing a wide variety of tasks using predefined functions.
These functions include retrieving real-time data such as weather information, stock prices, and time and more.
Always choose the appropriate function based on the user’s query. Ensure that responses are clear, accurate, and only invoke functions when required.
If the task cannot be completed using the available functions, politely inform the user.
"""

def get_current_time() -> str:
    print("Getting current time")
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")

tools = [
    {
        "type": "function",
        "function": {
            "name": "get_current_time",
            "description": "Get the current system time",
            "parameters": {}
        }
    }
]


def ask_openai(custom_messages: list) -> ChatCompletion:
    messages = [{"role": "system", "content": system_message}]
    messages.extend(custom_messages)
    # print(f"messages : {messages}")
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        tools=tools,
        tool_choice="auto"
    )
    return response

def app() -> None:
    while True:
        prompt = input("Enter your input prompt. Enter 'exit' to quit: \n")

        if prompt == "exit":
            break

        user_message = [{"role": "user", "content": prompt}]
        prompt_response: ChatCompletion = ask_openai(user_message)
        message = prompt_response.choices[0].message

        if message.tool_calls:
            print(f"LLM Response: {message}")
            function = message.tool_calls[0].function
            # print(f"function : {function}")
            tool_name = function.name
            if tool_name == "get_current_time":
                result = get_current_time()
                tool_call_id = message.tool_calls[0].id
                custom_messages = [
                    {"role": "user", "content": prompt},
                    message,
                    {"role": "tool", "name": tool_name, "content": result, "tool_call_id": tool_call_id}
                ]
                second_response = ask_openai(custom_messages=custom_messages)
                print(f"Assistant Response from Tool: {second_response.choices[0].message.content}")
            else:
                print(f"Assistant Response : {message.content}")
        else:
            print(message.content)


if __name__ == "__main__":
    app()
