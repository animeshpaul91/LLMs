# Transforming

In this notebook, we will explore how to use Large Language Models for text transformation tasks such as language translation, spelling and grammar checking, tone adjustment, and format conversion.

## Setup


```python
import openai
import os

from dotenv import load_dotenv, find_dotenv
_ = load_dotenv(find_dotenv()) # read local .env file

openai.api_key  = os.getenv('OPENAI_API_KEY')
```


```python
def get_completion(prompt, model="gpt-4o", temperature=0): 
    messages = [{"role": "user", "content": prompt}]
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=temperature, 
    )
    return response.choices[0].message["content"]
```

## Translation

ChatGPT is trained with sources in many languages. This gives the model the ability to do translation. Here are some examples of how to use this capability.


```python
prompt = f"""
Translate the following English text \
delimited by triple backticksto Spanish: \ 
```Hi, I would like to order a blender```
"""
response = get_completion(prompt)
print(response)
```

    Hola, me gustaría pedir una licuadora.



```python
prompt = f"""
Tell me which language this is: 
```Combien coûte le lampadaire?```
"""
response = get_completion(prompt)
print(response)
```

    The sentence "Combien coûte le lampadaire?" is in French. It translates to "How much does the floor lamp cost?" in English.



```python
prompt = f"""
Translate the following  text to French and Spanish
and English pirate: \
```I want to order a basketball```
"""
response = get_completion(prompt)
print(response)
```

    **French:** Je veux commander un ballon de basket.
    
    **Spanish:** Quiero pedir un balón de baloncesto.
    
    **English Pirate:** Arrr, I be wantin' to order a basketball, matey!



```python
prompt = f"""
Translate the following text to Spanish in both the \
formal and informal forms: 
'Would you like to order a pillow?'
"""
response = get_completion(prompt)
print(response)
```

    Informal: ¿Te gustaría pedir una almohada?
    
    Formal: ¿Le gustaría pedir una almohada?


### Universal Translator
Imagine you are in charge of IT at a large multinational e-commerce company. Users are messaging you with IT issues in all their native languages. Your staff is from all over the world and speaks only their native languages. You need a universal translator!


```python
user_messages = [
  "La performance du système est plus lente que d'habitude.",  # System performance is slower than normal         
  "Mi monitor tiene píxeles que no se iluminan.",              # My monitor has pixels that are not lighting
  "Il mio mouse non funziona",                                 # My mouse is not working
  "Mój klawisz Ctrl jest zepsuty",                             # My keyboard has a broken control key
  "我的屏幕在闪烁"                                               # My screen is flashing
] 
```


```python
for issue in user_messages:
    prompt = f"Just tell me what language this is. \
    Respond with only one word: ```{issue}```"
    lang = get_completion(prompt)
    print(f"Original message [{lang}]: {issue}")

    prompt = f"""
    Translate the following  text to English \
    and Bengali: ```{issue}```
    """
    response = get_completion(prompt)
    print(response, "\n")
```

    Original message [French]: La performance du système est plus lente que d'habitude.
    **English:** The system's performance is slower than usual.
    
    **Bengali:** সিস্টেমের কার্যক্ষমতা স্বাভাবিকের তুলনায় ধীর। 
    
    Original message [Spanish]: Mi monitor tiene píxeles que no se iluminan.
    **English:** My monitor has pixels that do not light up.
    
    **Bengali:** আমার মনিটরে এমন পিক্সেল আছে যা জ্বলে না। 
    
    Original message [Italian]: Il mio mouse non funziona
    Sure! Here is the translation of the text:
    
    **English:** "My mouse is not working."
    
    **Bengali:** "আমার মাউস কাজ করছে না।" 
    
    Original message [Polish]: Mój klawisz Ctrl jest zepsuty
    **English:** My Ctrl key is broken.
    
    **Bengali:** আমার Ctrl কীটি নষ্ট হয়ে গেছে। 
    
    Original message [Chinese]: 我的屏幕在闪烁
    **English:** "My screen is flickering."
    
    **Bengali:** "আমার স্ক্রিন ঝলকাচ্ছে।" 
    


## Try it yourself!
Try some translations on your own!


```python
input_prompt = ";".join(user_messages)

prompt = f"""
You are given an input text. Split this text with semicolon \
character first. For each split, You have to \
perform the following tasks and generate the output 
in a JSON format with the mentioned keys. Make every \
statement in the output JSON end with a full-stop. \

Here are the tasks:

* Identify the language and output it in one word under \
JSON key 'language'
* Put the original input message in JSON key 'input'
* Translate into English and output it in JSON key 'english'
* Translate into Bengali and output it in JSON key 'bengali'

Here is your input text delimited with triple backticks.
```{input_prompt}```
"""

response = get_completion(prompt)
print(response)
```

    ```json
    [
        {
            "language": "French.",
            "input": "La performance du système est plus lente que d'habitude.",
            "english": "The system's performance is slower than usual.",
            "bengali": "সিস্টেমের কর্মক্ষমতা স্বাভাবিকের চেয়ে ধীর।"
        },
        {
            "language": "Spanish.",
            "input": "Mi monitor tiene píxeles que no se iluminan.",
            "english": "My monitor has pixels that do not light up.",
            "bengali": "আমার মনিটরে এমন পিক্সেল রয়েছে যা জ্বলে না।"
        },
        {
            "language": "Italian.",
            "input": "Il mio mouse non funziona.",
            "english": "My mouse does not work.",
            "bengali": "আমার মাউস কাজ করছে না।"
        },
        {
            "language": "Polish.",
            "input": "Mój klawisz Ctrl jest zepsuty.",
            "english": "My Ctrl key is broken.",
            "bengali": "আমার Ctrl কীটি ভাঙা।"
        },
        {
            "language": "Chinese.",
            "input": "我的屏幕在闪烁",
            "english": "My screen is flickering.",
            "bengali": "আমার স্ক্রিন ঝলকাচ্ছে।"
        }
    ]
    ```


## Tone Transformation
Writing can vary based on the intended audience. ChatGPT can produce different tones.



```python
prompt = f"""
Translate the following from slang to a business letter to \
a recipient named 'Animesh Paul': 
'Dude, This is Joe, check out this spec on this standing lamp.'
"""
response = get_completion(prompt)
print(response)
```

    Subject: Product Specification for Standing Lamp
    
    Dear Mr. Paul,
    
    I hope this message finds you well. I am writing to share with you the specifications of a standing lamp that may be of interest to you. Please find the details attached for your review.
    
    Thank you for your attention.
    
    Best regards,
    
    Joe


## Format Conversion
ChatGPT can translate between formats. The prompt should describe the input and output formats.


```python
data_json = { "resturant employees" :[ 
    {"name":"Shyam", "email":"shyamjaiswal@gmail.com"},
    {"name":"Bob", "email":"bob32@gmail.com"},
    {"name":"Jai", "email":"jai87@gmail.com"}
]}

prompt = f"""
Translate the following python dictionary from JSON to an HTML \
table with column headers and title: {data_json}
"""
response = get_completion(prompt)
print(response)
```

    To convert the given JSON-like Python dictionary into an HTML table with column headers and a title, you can use the following HTML code:
    
    ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Restaurant Employees</title>
        <style>
            table {
                width: 50%;
                border-collapse: collapse;
                margin: 20px 0;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
    
        <h1>Restaurant Employees</h1>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Shyam</td>
                    <td>shyamjaiswal@gmail.com</td>
                </tr>
                <tr>
                    <td>Bob</td>
                    <td>bob32@gmail.com</td>
                </tr>
                <tr>
                    <td>Jai</td>
                    <td>jai87@gmail.com</td>
                </tr>
            </tbody>
        </table>
    
    </body>
    </html>
    ```
    
    This HTML code creates a table with a title "Restaurant Employees" and two columns: "Name" and "Email". Each row in the table corresponds to an employee from the provided dictionary.



```python
from IPython.display import display, Markdown, Latex, HTML, JSON
display(HTML(response))
```


To convert the given JSON-like Python dictionary into an HTML table with column headers and a title, you can use the following HTML code:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Employees</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

    <h1>Restaurant Employees</h1>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Shyam</td>
                <td>shyamjaiswal@gmail.com</td>
            </tr>
            <tr>
                <td>Bob</td>
                <td>bob32@gmail.com</td>
            </tr>
            <tr>
                <td>Jai</td>
                <td>jai87@gmail.com</td>
            </tr>
        </tbody>
    </table>

</body>
</html>
```

This HTML code creates a table with a title "Restaurant Employees" and two columns: "Name" and "Email". Each row in the table corresponds to an employee from the provided dictionary.



```python
data_json = { "resturant employees" :[ 
    {"name":"Shyam", "email":"shyamjaiswal@gmail.com"},
    {"name":"Bob", "email":"bob32@gmail.com"},
    {"name":"Jai", "email":"jai87@gmail.com"}
]}

prompt = f"""
Translate the following python dictionary from JSON to YAML \
format: {data_json}
"""
response = get_completion(prompt)
print(response)
```

    To translate the given JSON-like Python dictionary to YAML format, you can represent it as follows:
    
    ```yaml
    resturant employees:
      - name: Shyam
        email: shyamjaiswal@gmail.com
      - name: Bob
        email: bob32@gmail.com
      - name: Jai
        email: jai87@gmail.com
    ```
    
    This YAML format maintains the structure of the original dictionary, with each employee represented as a list item under the key `resturant employees`.


## Spellcheck/Grammar check.

Here are some examples of common grammar and spelling problems and the LLM's response. 

To signal to the LLM that you want it to proofread your text, you instruct the model to 'proofread' or 'proofread and correct'.


```python
text = [ 
  "The girl with the black and white puppies have a ball.",  # The girl has a ball.
  "Yolanda has her notebook.", # ok
  "Its going to be a long day. Does the car need it’s oil changed?",  # Homonyms
  "Their goes my freedom. There going to bring they’re suitcases.",  # Homonyms
  "Your going to need you’re notebook.",  # Homonyms
  "That medicine effects my ability to sleep. Have you heard of the butterfly affect?", # Homonyms
  "This phrase is to cherck chatGPT for speling abilitty"  # spelling
]

for i in range(0, len(text)):
    t = text[i]
    prompt = f"""Proofread and correct the following text
    and rewrite the corrected version. If you don't find
    any errors, just say "No errors found". Don't use 
    any punctuation around the text:
    ```{t}```"""
    response = get_completion(prompt)
    print(i, response)
```

    0 The girl with the black and white puppies has a ball.
    1 No errors found
    2 Its going to be a long day. Does the car need its oil changed?
    
    It's going to be a long day. Does the car need its oil changed?
    3 There goes my freedom. They're going to bring their suitcases.
    4 You're going to need your notebook.
    5 That medicine affects my ability to sleep. Have you heard of the butterfly effect?
    6 This phrase is to check ChatGPT for spelling ability



```python
text = f"""
Got this for my daughter for her birthday cuz she keeps taking \
mine from my room.  Yes, adults also like pandas too.  She takes \
it everywhere with her, and it's super soft and cute.  One of the \
ears is a bit lower than the other, and I don't think that was \
designed to be asymmetrical. It's a bit small for what I paid for it \
though. I think there might be other options that are bigger for \
the same price.  It arrived a day earlier than expected, so I got \
to play with it myself before I gave it to my daughter.
"""
prompt = f"proofread and correct this review: ```{text}```"
response = get_completion(prompt)
print(response)
```

    Got this for my daughter for her birthday because she keeps taking mine from my room. Yes, adults like pandas too. She takes it everywhere with her, and it's super soft and cute. One of the ears is a bit lower than the other, and I don't think it was designed to be asymmetrical. It's a bit small for what I paid for it, though. I think there might be other options that are bigger for the same price. It arrived a day earlier than expected, so I got to play with it myself before I gave it to my daughter.



```python
from redlines import Redlines

diff = Redlines(text,response)
display(Markdown(diff.output_markdown))
```


Got this for my daughter for her birthday <span style="color:red;font-weight:700;text-decoration:line-through;">cuz </span><span style="color:red;font-weight:700;">because </span>she keeps taking mine from my <span style="color:red;font-weight:700;text-decoration:line-through;">room.  </span><span style="color:red;font-weight:700;">room. </span>Yes, adults <span style="color:red;font-weight:700;text-decoration:line-through;">also </span>like pandas <span style="color:red;font-weight:700;text-decoration:line-through;">too.  </span><span style="color:red;font-weight:700;">too. </span>She takes it everywhere with her, and it's super soft and <span style="color:red;font-weight:700;text-decoration:line-through;">cute.  </span><span style="color:red;font-weight:700;">cute. </span>One of the ears is a bit lower than the other, and I don't think <span style="color:red;font-weight:700;text-decoration:line-through;">that </span><span style="color:red;font-weight:700;">it </span>was designed to be asymmetrical. It's a bit small for what I paid for <span style="color:red;font-weight:700;text-decoration:line-through;">it </span><span style="color:red;font-weight:700;">it, </span>though. I think there might be other options that are bigger for the same <span style="color:red;font-weight:700;text-decoration:line-through;">price.  </span><span style="color:red;font-weight:700;">price. </span>It arrived a day earlier than expected, so I got to play with it myself before I gave it to my <span style="color:red;font-weight:700;text-decoration:line-through;">daughter.
</span><span style="color:red;font-weight:700;">daughter.</span>



```python
prompt = f"""
proofread and correct this review. Make it more compelling. 
Ensure it follows APA style guide and targets an advanced reader. 
Output in markdown format.
Text: ```{text}```
"""
response = get_completion(prompt)
display(Markdown(response))
```


```markdown
I purchased this panda plush toy for my daughter's birthday, as she frequently borrowed mine from my room. It is important to note that pandas are beloved by adults as well. My daughter takes this plush toy everywhere, and its softness and cuteness are truly remarkable. However, I observed that one of the ears is slightly lower than the other, which does not appear to be an intentional design choice. Additionally, the size of the plush toy is somewhat smaller than anticipated, considering the price. There may be larger alternatives available at a similar cost. On a positive note, the item arrived a day earlier than expected, allowing me the opportunity to enjoy it briefly before gifting it to my daughter.

In summary, while the panda plush toy is undeniably charming and adored by both children and adults, potential buyers should be aware of its minor asymmetry and relatively small size. Despite these considerations, the early delivery was a pleasant surprise, enhancing the overall purchasing experience.
```



## Try it yourself!
Try changing the instructions to form your own review.


```python
prompt = "What is APA style?"

response = get_completion(prompt)
display(Markdown(response))
```


APA style is a set of guidelines for writing and formatting research papers and reports, developed by the American Psychological Association. It is widely used in the social sciences, education, and other fields. The main components of APA style include:

1. **Formatting**: This includes specific guidelines for margins, font size and type (typically Times New Roman, 12-point), line spacing (double-spaced), and page headers.

2. **Title Page**: An APA-style paper usually includes a title page with the title of the paper, the author's name, and the institutional affiliation.

3. **Abstract**: A brief summary of the research paper, usually no more than 250 words, that provides an overview of the main points.

4. **Headings**: APA style uses a specific system of headings and subheadings to organize content, which helps in structuring the paper clearly.

5. **In-text Citations**: When referencing other works, APA style uses the author-date citation method, where the author's last name and the year of publication are included in the text.

6. **Reference List**: At the end of the document, a reference list provides full details of all sources cited in the paper. This includes the author's name, publication year, title of the work, and publication details.

7. **Tables and Figures**: APA style provides specific guidelines for the presentation of tables and figures, including labeling and captioning.

8. **Bias-Free Language**: APA emphasizes the use of inclusive and bias-free language, encouraging writers to avoid language that might be considered discriminatory or biased.

These guidelines are detailed in the "Publication Manual of the American Psychological Association," which is regularly updated to reflect changes in writing and publishing standards. The most recent edition, as of my knowledge cutoff in October 2023, is the 7th edition.


Thanks to the following sites:

https://writingprompts.com/bad-grammar-examples/

