import requests

from dotenv import load_dotenv
from openai import OpenAI
from openai.types import ImagesResponse

load_dotenv()

client = OpenAI()
LLM = "gpt-4o"
image_url = "https://www.invoicesimple.com/wp-content/uploads/2018/06/Sample-Invoice-printable.png"


# Call the openai chat.completions endpoint
def ask_openai(user_question: str) -> ImagesResponse:
    print(f"LLM : {LLM}")
    response = client.images.generate(
        model="dall-e-3",
        prompt=user_question,
        size="1024x1024",
        quality="hd",
        #n=1,
    )
    print(f"response  type : {type(response)}")
    return response


if __name__ == "__main__":
    # Step 4 :
    input_prompt = "Generate an image of a beautiful lake with a mountain in the background."
    llm_response: ImagesResponse = ask_openai(input_prompt)
    image_url = llm_response.data[0].url

    image_data = requests.get(image_url).content
    file_name = "generated_image.png"

    with open(f"resources/{file_name}", "wb") as binary_file:
        binary_file.write(image_data)

    print(f"File was successfully generated with name {file_name}")
