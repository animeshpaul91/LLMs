# Guidelines for Prompting
In this lesson, you'll practice two prompting principles and their related tactics in order to write effective prompts for large language models.

## Setup
#### Load the API key and relevant Python libaries.

In this course, we've provided some code that loads the OpenAI API key for you.


```python
import openai
import os

from dotenv import load_dotenv, find_dotenv
_ = load_dotenv(find_dotenv())

openai.api_key  = os.getenv('OPENAI_API_KEY')
```

#### helper function
Throughout this course, we will use OpenAI's `gpt-3.5-turbo` model and the [chat completions endpoint](https://platform.openai.com/docs/guides/chat). 

This helper function will make it easier to use prompts and look at the generated outputs.  
**Note**: In June 2023, OpenAI updated gpt-3.5-turbo. The results you see in the notebook may be slightly different than those in the video. Some of the prompts have also been slightly modified to product the desired results.


```python
def get_completion(prompt, model="gpt-4o"):
    messages = [{"role": "user", "content": prompt}]
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0, # this is the degree of randomness of the model's output
    )
    return response.choices[0].message["content"]
```

**Note:** This and all other lab notebooks of this course use OpenAI library version `0.27.0`. 

In order to use the OpenAI library version `1.0.0`, here is the code that you would use instead for the `get_completion` function:

```python
client = openai.OpenAI()

def get_completion(prompt, model="gpt-3.5-turbo"):
    messages = [{"role": "user", "content": prompt}]
    response = client.chat.completions.create(
        model=model,
        messages=messages,
        temperature=0
    )
    return response.choices[0].message.content
```

## Prompting Principles
- **Principle 1: Write clear and specific instructions**
- **Principle 2: Give the model time to “think”**

### Tactics

#### Tactic 1: Use delimiters to clearly indicate distinct parts of the input
- Delimiters can be anything like: ```, """, < >, `<tag> </tag>`, `:`


```python
text = f"""
You should express what you want a model to do by \ 
providing instructions that are as clear and \ 
specific as you can possibly make them. \ 
This will guide the model towards the desired output, \ 
and reduce the chances of receiving irrelevant \ 
or incorrect responses. Don't confuse writing a \ 
clear prompt with writing a short prompt. \ 
In many cases, longer prompts provide more clarity \ 
and context for the model, which can lead to \ 
more detailed and relevant outputs.
"""
prompt = f"""
Summarize the text delimited by triple backticks \ 
into a single sentence.
```{text}```
"""

# print(prompt)
response = get_completion(prompt)
print(response)
```

    To achieve the desired output from a model, provide clear and specific instructions, as longer prompts often offer better clarity and context, reducing irrelevant or incorrect responses.


#### Tactic 2: Ask for a structured output
- JSON, HTML


```python
prompt = f"""
Generate a list of three made-up book titles along \ 
with their authors and genres. 
Provide them in JSON format with the following keys: 
book_id, title, author, genre.
"""
response = get_completion(prompt)
print(response)
```

    ```json
    [
        {
            "book_id": 1,
            "title": "Whispers of the Forgotten Forest",
            "author": "Elara Moonstone",
            "genre": "Fantasy"
        },
        {
            "book_id": 2,
            "title": "The Quantum Detective",
            "author": "Maxwell Hawthorne",
            "genre": "Science Fiction"
        },
        {
            "book_id": 3,
            "title": "Echoes of a Silent City",
            "author": "Lydia Winters",
            "genre": "Mystery"
        }
    ]
    ```


#### Tactic 3: Ask the model to check whether conditions are satisfied


```python
text_1 = f"""
Making a cup of tea is easy! First, you need to get some \ 
water boiling. While that's happening, \ 
grab a cup and put a tea bag in it. Once the water is \ 
hot enough, just pour it over the tea bag. \ 
Let it sit for a bit so the tea can steep. After a \ 
few minutes, take out the tea bag. If you \ 
like, you can add some sugar or milk to taste. \ 
And that's it! You've got yourself a delicious \ 
cup of tea to enjoy.
"""
prompt = f"""
You will be provided with text delimited by triple quotes. 
If it contains a sequence of instructions, \ 
re-write those instructions in the following format:

Step 1 - ...
Step 2 - …
…
Step N - …

If the text does not contain a sequence of instructions, \ 
then simply write \"No steps provided.\"

\"\"\"{text_1}\"\"\"
"""
response = get_completion(prompt)
print("Completion for Text 1:")
print(response)
```

    Completion for Text 1:
    Step 1 - Get some water boiling.  
    Step 2 - Grab a cup and put a tea bag in it.  
    Step 3 - Once the water is hot enough, pour it over the tea bag.  
    Step 4 - Let it sit for a bit so the tea can steep.  
    Step 5 - After a few minutes, take out the tea bag.  
    Step 6 - If you like, add some sugar or milk to taste.



```python
text_2 = f"""
The sun is shining brightly today, and the birds are \
singing. It's a beautiful day to go for a \ 
walk in the park. The flowers are blooming, and the \ 
trees are swaying gently in the breeze. People \ 
are out and about, enjoying the lovely weather. \ 
Some are having picnics, while others are playing \ 
games or simply relaxing on the grass. It's a \ 
perfect day to spend time outdoors and appreciate the \ 
beauty of nature.
"""
prompt = f"""
You will be provided with text delimited by triple quotes. 
If it contains a sequence of instructions, \ 
re-write those instructions in the following format:

Step 1 - ...
Step 2 - …
…
Step N - …

If the text does not contain a sequence of instructions, \ 
then simply write \"No steps provided.\"

\"\"\"{text_2}\"\"\"
"""
response = get_completion(prompt)
print("Completion for Text 2:")
print(response)
```

    Completion for Text 2:
    No steps provided.


#### Tactic 4: "Few-shot" prompting


```python
prompt = f"""
Your task is to answer in a consistent style.

<child>: Teach me about patience.

<grandparent>: The river that carves the deepest \ 
valley flows from a modest spring; the \ 
grandest symphony originates from a single note; \ 
the most intricate tapestry begins with a solitary thread.

<child>: Teach me about resilience.
"""
response = get_completion(prompt)
print(response)
```

    <grandparent>: The mighty oak stands tall through \ 
    the fiercest storms, its roots anchored deep in the \ 
    earth; the phoenix rises anew from the ashes, \ 
    embracing each dawn with renewed strength; \ 
    the mountain endures the test of time, its peaks \ 
    unwavering against the winds of change.


### Principle 2: Give the model time to “think” 

#### Tactic 1: Specify the steps required to complete a task


```python
text = f"""
In a charming village, siblings Jack and Jill set out on \ 
a quest to fetch water from a hilltop \ 
well. As they climbed, singing joyfully, misfortune \ 
struck—Jack tripped on a stone and tumbled \ 
down the hill, with Jill following suit. \ 
Though slightly battered, the pair returned home to \ 
comforting embraces. Despite the mishap, \ 
their adventurous spirits remained undimmed, and they \ 
continued exploring with delight.
"""
# example 1
prompt_1 = f"""
Perform the following actions: 
1 - Summarize the following text delimited by triple \
backticks with 1 sentence.
2 - Translate the summary into Bengali.
3 - List each name in the Bengali summary. Output the names in bengali
4 - Output a json object that contains the following \
keys: bengali_summary, num_names.

Separate your answers with line breaks.

Text:
```{text}```
"""
response = get_completion(prompt_1)
print("Completion for prompt 1:")
print(response)
```

    Completion for prompt 1:
    1 - Siblings Jack and Jill embarked on a joyful quest to fetch water from a hilltop well, but after a mishap where they both tumbled down, they returned home slightly battered yet undeterred in their adventurous spirits.
    
    2 - ভাইবোন জ্যাক এবং জিল একটি আনন্দময় অভিযানে পাহাড়ের চূড়ার কুয়ো থেকে জল আনতে বের হয়েছিল, কিন্তু একটি দুর্ঘটনার পরে যেখানে তারা উভয়েই গড়িয়ে পড়েছিল, তারা সামান্য আহত অবস্থায় বাড়ি ফিরে আসে, তবুও তাদের সাহসী মনোবল অটুট ছিল।
    
    3 - জ্যাক, জিল
    
    4 - 
    ```json
    {
      "bengali_summary": "ভাইবোন জ্যাক এবং জিল একটি আনন্দময় অভিযানে পাহাড়ের চূড়ার কুয়ো থেকে জল আনতে বের হয়েছিল, কিন্তু একটি দুর্ঘটনার পরে যেখানে তারা উভয়েই গড়িয়ে পড়েছিল, তারা সামান্য আহত অবস্থায় বাড়ি ফিরে আসে, তবুও তাদের সাহসী মনোবল অটুট ছিল।",
      "num_names": 2
    }
    ```


#### Ask for output in a specified format


```python
prompt_2 = f"""
Your task is to perform the following actions: 
1 - Summarize the following text delimited by 
  <> with 1 sentence.
2 - Translate the summary into French.
3 - List each name in the French summary.
4 - Output a json object that contains the 
  following keys: french_summary, num_names.

Use the following format:
Text to Summarize: <text to summarize>
Summary: <summary>
French Translation: <summary translation>
Names: <list of names in summary>
Output JSON: <json with summary and num_names>

Text: <{text}>
"""
response = get_completion(prompt_2)
print("\nCompletion for prompt 2:")
print(response)
```

    
    Completion for prompt 2:
    Text to Summarize: In a charming village, siblings Jack and Jill set out on a quest to fetch water from a hilltop well. As they climbed, singing joyfully, misfortune struck—Jack tripped on a stone and tumbled down the hill, with Jill following suit. Though slightly battered, the pair returned home to comforting embraces. Despite the mishap, their adventurous spirits remained undimmed, and they continued exploring with delight.
    
    Summary: Siblings Jack and Jill went on an adventure to fetch water from a hilltop well, but after a fall, they returned home safely and continued their explorations with enthusiasm.
    
    French Translation: Les frères et sœurs Jack et Jill sont partis à l'aventure pour chercher de l'eau à un puits au sommet d'une colline, mais après une chute, ils sont rentrés chez eux sains et saufs et ont continué leurs explorations avec enthousiasme.
    
    Names: Jack, Jill
    
    Output JSON: {"french_summary": "Les frères et sœurs Jack et Jill sont partis à l'aventure pour chercher de l'eau à un puits au sommet d'une colline, mais après une chute, ils sont rentrés chez eux sains et saufs et ont continué leurs explorations avec enthousiasme.", "num_names": 2}


#### Tactic 2: Instruct the model to work out its own solution before rushing to a conclusion


```python
prompt = f"""
Determine if the student's solution is correct or not.

Question:
I'm building a solar power installation and I need \
 help working out the financials. 
- Land costs $100 / square foot
- I can buy solar panels for $250 / square foot
- I negotiated a contract for maintenance that will cost \ 
me a flat $100k per year, and an additional $10 / square \
foot
What is the total cost for the first year of operations 
as a function of the number of square feet.

Student's Solution:
Let x be the size of the installation in square feet.
Costs:
1. Land cost: 100x
2. Solar panel cost: 250x
3. Maintenance cost: 100,000 + 100x
Total cost: 100x + 250x + 100,000 + 100x = 450x + 100,000
"""
response = get_completion(prompt)
print(response)
```

    The student's solution is correct. The total cost for the first year of operations as a function of the number of square feet \( x \) is calculated as follows:
    
    1. **Land cost**: \( 100x \) (since land costs $100 per square foot)
    2. **Solar panel cost**: \( 250x \) (since solar panels cost $250 per square foot)
    3. **Maintenance cost**: \( 100,000 + 10x \) (a flat $100,000 per year plus $10 per square foot)
    
    The total cost is the sum of these three components:
    
    \[
    100x + 250x + 100,000 + 10x = 360x + 100,000
    \]
    
    The student made an error in the maintenance cost calculation. The correct maintenance cost should be \( 100,000 + 10x \), not \( 100,000 + 100x \). Therefore, the correct total cost should be:
    
    \[
    360x + 100,000
    \]
    
    The student's final expression \( 450x + 100,000 \) is incorrect. The correct expression is \( 360x + 100,000 \).


#### Note that the student's solution is actually not correct.
#### We can fix this by instructing the model to work out its own solution first.


```python
prompt = f"""
Your task is to determine if the student's solution \
is correct or not.
To solve the problem do the following:
- First, work out your own solution to the problem including the final total. 
- Then compare your solution to the student's solution \ 
and evaluate if the student's solution is correct or not. 
Don't decide if the student's solution is correct until 
you have done the problem yourself.

Use the following format for your response:
Question:
```
question here
```
Student's solution:
```
student's solution here
```
Actual solution:
```
steps to work out the solution and your solution here
```
Is the student's solution the same as actual solution \
just calculated:
```
yes or no
```
Student grade:
```
correct or incorrect
```

Question:
```
I'm building a solar power installation and I need help \
working out the financials. 
- Land costs $100 / square foot
- I can buy solar panels for $250 / square foot
- I negotiated a contract for maintenance that will cost \
me a flat $100k per year, and an additional $10 / square \
foot
What is the total cost for the first year of operations \
as a function of the number of square feet.
``` 
Student's solution:
```
Let x be the size of the installation in square feet.
Costs:
1. Land cost: 100x
2. Solar panel cost: 250x
3. Maintenance cost: 100,000 + 100x
Total cost: 100x + 250x + 100,000 + 100x = 450x + 100,000
```
Actual solution:
"""
response = get_completion(prompt)
print(response)
```

    ```
    Let x be the size of the installation in square feet.
    Costs:
    1. Land cost: 100x (since land costs $100 per square foot)
    2. Solar panel cost: 250x (since solar panels cost $250 per square foot)
    3. Maintenance cost: 100,000 + 10x (since the maintenance costs a flat $100k per year plus $10 per square foot)
    Total cost: 100x + 250x + 100,000 + 10x = 360x + 100,000
    ```
    Is the student's solution the same as actual solution just calculated:
    ```
    no
    ```
    Student grade:
    ```
    incorrect
    ```


## Model Limitations: Hallucinations
- Boie is a real company, the product name is not real.


```python
## Real Company, but fake product

prompt = f"""
Tell me about AeroGlide UltraSlim Smart Toothbrush by Boie
"""
response = get_completion(prompt)
print(response)
```

    As of my last update, there isn't specific information available about a product called the "AeroGlide UltraSlim Smart Toothbrush" by Boie. Boie is known for producing eco-friendly personal care products, including toothbrushes made from sustainable materials. Their products often focus on being environmentally friendly, with features like replaceable heads and materials that are gentle on the gums.
    
    If the AeroGlide UltraSlim Smart Toothbrush is a new release or a product that has been announced after my last update, I recommend checking Boie's official website or their latest press releases for the most accurate and up-to-date information. Additionally, you might find reviews or product details on major retail websites or tech news outlets.


## Try experimenting on your own!


```python

```

#### Notes on using the OpenAI API outside of this classroom

To install the OpenAI Python library:
```
!pip install openai
```

The library needs to be configured with your account's secret key, which is available on the [website](https://platform.openai.com/account/api-keys). 

You can either set it as the `OPENAI_API_KEY` environment variable before using the library:
 ```
 !export OPENAI_API_KEY='sk-...'
 ```

Or, set `openai.api_key` to its value:

```
import openai
openai.api_key = "sk-..."
```

#### A note about the backslash
- In the course, we are using a backslash `\` to make the text fit on the screen without inserting newline '\n' characters.
- GPT-3 isn't really affected whether you insert newline characters or not.  But when working with LLMs in general, you may consider whether newline characters in your prompt may affect the model's performance.
