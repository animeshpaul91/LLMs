import json
import os
from datetime import datetime, timezone

import requests
from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

from model.weather_model import OpenMeteoInput

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")

system_message = """You are an intelligent assistant capable of performing a wide variety of tasks using predefined functions.
These functions include retrieving real-time data such as weather information, stock prices, and time and more.
Always choose the appropriate function based on the user’s query. Ensure that responses are clear, accurate, and only invoke functions when required.
If the task cannot be completed using the available functions, politely inform the user.
"""

tools = [
    {
        "type": "function",
        "function": {
            "name": "get_current_weather",
            "description": "Fetch current temperature/weather for given coordinates.",
            "parameters": {
                "type": "object",
                "properties": {
                    "latitude": {
                        "type": "number",
                        "description": "The latitude of the location to get the weather for.",
                    },
                    "longitude": {
                        "type": "number",
                        "description": "The longitude of the location to get the weather for.",
                    },
                },
                "required": ["latitude", "longitude"],
            }
        }
    }
]


def get_current_weather(openMeteoInput: OpenMeteoInput):
    """Fetch current temperature for given coordinates."""

    BASE_URL = "https://api.open-meteo.com/v1/forecast"

    # Parameters for the request
    params = {
        "latitude": openMeteoInput.latitude,
        "longitude": openMeteoInput.longitude,
        "hourly": "temperature_2m",
        "forecast_days": 1,
    }

    # Make the request
    response = requests.get(BASE_URL, params=params)

    if response.status_code == 200:
        results = response.json()
    else:
        raise Exception(f"API Request failed with status code: {response.status_code}")
    # print(f"results : {results}")

    time_list = [
        datetime.fromisoformat(time_str.replace("Z", "+00:00")).replace(
            tzinfo=timezone.utc
        )
        for time_str in results["hourly"]["time"]
    ]
    # print(f"time_list : {time_list}")
    temperature_list = results["hourly"]["temperature_2m"]

    current_utc_time = datetime.now(timezone.utc)
    closest_time_index = min(
        range(len(time_list)), key=lambda i: abs(time_list[i] - current_utc_time)
    )

    current_temperature = temperature_list[closest_time_index]
    return f"The current temperature is {current_temperature}°C"


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
            print(f"function : {function}")
            # Extract tool name and arguments
            arguments = json.loads(function.arguments)  # Parse JSON string to dictionary
            # print(f" get_current_weather arguments : {arguments}")
            # Execute the tool
            latitude = arguments.get("latitude")
            longitude = arguments.get("longitude")
            open_meteo_input = OpenMeteoInput(
                latitude=latitude, longitude=longitude
            )
            result = get_current_weather(open_meteo_input)
            # Send the result back to the model
            tool_name = function.name
            tool_call_id = message.tool_calls[0].id  # Get the tool_call_id
            custom_messages = [
                {"role": "user", "content": prompt},
                message,
                {
                    "role": "tool",
                    "name": tool_name,
                    "content": result,
                    "tool_call_id": tool_call_id,  # Include the tool_call_id
                },
            ]
            second_response = ask_openai(custom_messages=custom_messages)
            # Print the model's final response
            final_message = second_response.choices[0].message.content
            print(f"Final Assistant: {final_message}")
        else:
            print(message.content)


if __name__ == "__main__":
    app()
