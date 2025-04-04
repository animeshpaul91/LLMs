import requests
from dotenv import load_dotenv
from openai import OpenAI
from openai.types import ImagesResponse

load_dotenv()

client = OpenAI()
LLM = "gpt-4o"


# Call the openai chat.completions endpoint
def ask_openai(size="1024x1024") -> ImagesResponse:
    print(f"LLM : {LLM}")

    response = client.images.create_variation(
        model="dall-e-2",
        image=open("resources/generated_image.png", "rb"),  # image is sent in binary format
        n=1,
        size=size,
    )

    print(f"response  type : {type(response)}")
    return response


if __name__ == "__main__":
    llm_response: ImagesResponse = ask_openai()

    # Pretty print the entire response
    image_url = llm_response.data[0].url
    print("Generated Image URL:", image_url)

    image_data = requests.get(image_url).content
    file_name = "image_variation.png"

    with open(f"resources/{file_name}", "wb") as f:
        f.write(image_data)

    print(f"Image successfully downloaded and saved as {file_name}")
