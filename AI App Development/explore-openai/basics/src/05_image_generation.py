from dotenv import load_dotenv
from openai import OpenAI
from openai.types.chat.chat_completion import ChatCompletion

load_dotenv()

client = OpenAI()
LLM = "gpt-4o"
image_url = "https://www.invoicesimple.com/wp-content/uploads/2018/06/Sample-Invoice-printable.png"


# Call the openai chat.completions endpoint
def ask_openai(user_question: str) -> ChatCompletion:
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
    input_prompt = "Generate an image of a dog and a cat sitting together."
    llm_response: ChatCompletion = ask_openai(input_prompt)
    image_url = llm_response.data[0].url
    print("Generated Image URL:", image_url)