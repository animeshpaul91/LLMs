from dotenv import load_dotenv
from openai import OpenAI
from openai._legacy_response import HttpxBinaryResponseContent

load_dotenv()

client = OpenAI()


def ask_openai(speech_text: str,
               voice: str = "alloy") -> HttpxBinaryResponseContent:
    response: HttpxBinaryResponseContent = client.audio.speech.create(
        model="tts-1",
        voice=voice,
        input=speech_text
    )
    return response


# Writing the streamed response to a file

if __name__ == "__main__":
    # French
    # audio_path ="resources/generated_audio_french.mp3"
    # speech_text= """
    #     Pierre est ingénieur logiciel et vit en France avec sa femme, ses deux filles et leur chien bien-aimé.
    #     Il accorde beaucoup d'importance au temps passé en famille, profitant souvent des week-ends remplis de rires et d'activités avec ses proches.
    #     En plus d'être un homme de famille dévoué, Pierre est passionné par le football et y joue régulièrement pendant son temps libre.
    #     Équilibrant travail et loisirs, il chérit chaque instant passé à la fois sur le terrain et à la maison, là où son cœur appartient vraiment.
    # """

    # English
    # audio_path = "resources/generated_audio_english.mp3"
    # speech_text = """
    #         Peter is a software engineer living in France with his wife, two daughters, and their beloved dog.
    #     He deeply values family time, often enjoying weekends filled with laughter and activities with his loved ones.
    #     In addition to being a devoted family man, Pierre has a passion for soccer and plays regularly in his free time.
    #     Balancing work and play, he cherishes every moment spent both on the field and at home, where his heart truly belongs.
    # """

    # Bengali
    audio_path = "resources/generated_audio_bengali.mp3"
    speech_text ="""
    নিশ্চয়ই! এখানে একটি সাধারণ বাংলা কথোপকথনের ট্রান্সক্রিপ্ট দিচ্ছি – ধরো, এটি দুই বন্ধুর মধ্যে দেখা হওয়ার পরের কথোপকথন:
    """
    response: HttpxBinaryResponseContent = ask_openai(speech_text=speech_text,
                                                      voice="alloy")
    with open(audio_path, 'wb') as audio_file:
        for chunk in response.iter_bytes():
            audio_file.write(chunk)

    print(f"{audio_path} , file generated successfully!")
