import os

from dotenv import load_dotenv
from openai import OpenAI
from openai.resources.audio.transcriptions import Transcription

load_dotenv()

client = OpenAI()
LLM = os.environ.get("OPEN_AI_MODEL")

# Call the openai chat.completions endpoint
def ask_openai(audio_file_path: str,
               prompt: str = "") :
    print(f"LLM : {LLM}")
    file = open(audio_file_path, "rb")
    transcription: Transcription = client.audio.transcriptions.create(
        model="whisper-1",
        file=file,
        prompt=prompt
    )
    return transcription


if __name__ == "__main__":
    audio_path = "resources/Taylor_Swift_Delicate.mp3"
    # audio_path = "resources/RAG.mp3"
    # YT_Link -> https://www.youtube.com/watch?v=T-D1OfcDW1M&t=20s

    input_prompt = "This audio explains the concepts about RAG"
    response: Transcription = ask_openai(audio_file_path=audio_path, prompt=input_prompt)
    transcription_text = response.text
    print(f"Transcription_text : {transcription_text}")
