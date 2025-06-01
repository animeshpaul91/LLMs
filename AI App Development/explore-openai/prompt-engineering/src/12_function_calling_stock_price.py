import json
import os

import yfinance as yf
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


def get_current_stock_price(ticker_symbol: str) -> str:
    ticker = yf.Ticker(ticker_symbol)
    today_data = ticker.history(period="1d")
    # print(today_data.to_string(index=False))
    return f"The stock price of {ticker_symbol} is,  {today_data['Close'].iloc[0]}"


tools = [
    {
        "type": "function",
        "function": {
            "name": "get_current_stock_price",
            "description": "Get the current stock price for a given ticker symbol.",
            "parameters": {
                "type": "object",
                "properties": {
                    "ticker_symbol": {
                        "type": "string",
                        "description": "The ticker symbol to get the stock price for.",
                    },
                },
                "required": ["ticker_symbol"]
            }
        }
    }
]


def ask_openai(custom_messages: list) -> ChatCompletion:
    messages = [{"role": "system", "content": system_message}]
    messages.extend(custom_messages)
    # print(f"messages : {messages}")
    response = client.chat.completions.create(
        model=LLM,
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
            arguments = json.loads(function.arguments)
            ticker_symbol = arguments.get("ticker_symbol")
            # print(f"function : {function}")
            tool_name = function.name
            result = get_current_stock_price(ticker_symbol)
            tool_call_id = message.tool_calls[0].id
            custom_messages = [
                {"role": "user", "content": prompt},
                message,
                {"role": "tool", "name": tool_name, "content": result, "tool_call_id": tool_call_id}
            ]
            second_response = ask_openai(custom_messages=custom_messages)
            print(f"Assistant Response from Tool: {second_response.choices[0].message.content}")
        else:
            print(message.content)


if __name__ == "__main__":
    app()
