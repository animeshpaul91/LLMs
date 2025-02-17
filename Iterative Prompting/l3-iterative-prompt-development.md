# Iterative Prompt Development
In this lesson, you'll iteratively analyze and refine your prompts to generate marketing copy from a product fact sheet.

## Setup


```python
import openai
import os

from dotenv import load_dotenv, find_dotenv
_ = load_dotenv(find_dotenv()) # read local .env file

openai.api_key  = os.getenv('OPENAI_API_KEY')
```


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

**Note**: In June 2023, OpenAI updated gpt-3.5-turbo. The results you see in the notebook may be slightly different than those in the video. Some of the prompts have also been slightly modified to product the desired results.

## Generate a marketing product description from a product fact sheet


```python
fact_sheet_chair = """
OVERVIEW
- Part of a beautiful family of mid-century inspired office furniture, 
including filing cabinets, desks, bookcases, meeting tables, and more.
- Several options of shell color and base finishes.
- Available with plastic back and front upholstery (SWC-100) 
or full upholstery (SWC-110) in 10 fabric and 6 leather options.
- Base finish options are: stainless steel, matte black, 
gloss white, or chrome.
- Chair is available with or without armrests.
- Suitable for home or business settings.
- Qualified for contract use.

CONSTRUCTION
- 5-wheel plastic coated aluminum base.
- Pneumatic chair adjust for easy raise/lower action.

DIMENSIONS
- WIDTH 53 CM | 20.87”
- DEPTH 51 CM | 20.08”
- HEIGHT 80 CM | 31.50”
- SEAT HEIGHT 44 CM | 17.32”
- SEAT DEPTH 41 CM | 16.14”

OPTIONS
- Soft or hard-floor caster options.
- Two choices of seat foam densities: 
 medium (1.8 lb/ft3) or high (2.8 lb/ft3)
- Armless or 8 position PU armrests 

MATERIALS
SHELL BASE GLIDER
- Cast Aluminum with modified nylon PA6/PA66 coating.
- Shell thickness: 10 mm.
SEAT
- HD36 foam

COUNTRY OF ORIGIN
- Italy
"""
```


```python
prompt = f"""
Your task is to help a marketing team create a 
description for a retail website of a product based 
on a technical fact sheet.

Write a product description based on the information 
provided in the technical specifications delimited by 
triple backticks.

Technical specifications: ```{fact_sheet_chair}```
"""
response = get_completion(prompt)
print(response)

```

    Elevate your workspace with our exquisite mid-century inspired office chair, a perfect blend of style and functionality. Part of a stunning collection of office furniture, this chair is designed to complement any home or business setting. Choose from a variety of shell colors and base finishes to match your decor, with options including stainless steel, matte black, gloss white, or chrome.
    
    Crafted with a 5-wheel plastic coated aluminum base, this chair offers smooth mobility and stability. The pneumatic adjust feature allows for effortless height adjustments, ensuring comfort throughout your workday. With dimensions of 53 cm in width, 51 cm in depth, and 80 cm in height, it fits seamlessly into any office space.
    
    Customize your seating experience with options for plastic back and front upholstery (SWC-100) or full upholstery (SWC-110), available in 10 fabric and 6 leather choices. Opt for soft or hard-floor casters and select from two seat foam densities—medium or high—for personalized comfort. The chair is available with or without armrests, featuring 8 position PU armrests for added versatility.
    
    Constructed with a durable cast aluminum shell and a modified nylon PA6/PA66 coating, this chair is built to last. The HD36 foam seat ensures long-lasting comfort and support. Proudly made in Italy, this chair is not only a stylish addition to your office but also qualified for contract use, making it a reliable choice for any professional environment.


## Issue 1: The text is too long 
- Limit the number of words/sentences/characters.


```python
prompt = f"""
Your task is to help a marketing team create a 
description for a retail website of a product based 
on a technical fact sheet.

Write a product description based on the information 
provided in the technical specifications delimited by 
triple backticks.

Please use at most 50 words.

Technical specifications: ```{fact_sheet_chair}```
"""
response = get_completion(prompt)
print(response)

```

    Discover the elegance of mid-century design with our versatile office chair. Choose from various shell colors, base finishes, and upholstery options. Featuring a 5-wheel aluminum base, pneumatic height adjustment, and optional armrests, it's perfect for home or business use. Crafted in Italy for style and durability.



```python
len(response.split())
```




    46



## Issue 2. Text focuses on the wrong details
- Ask it to focus on the aspects that are relevant to the intended audience.


```python
prompt = f"""
Your task is to help a marketing team create a 
description for a retail website of a product based 
on a technical fact sheet.

Write a product description based on the information 
provided in the technical specifications delimited by 
triple backticks.

The description is intended for furniture retailers,
and not for end customers, so should be technical in nature 
and focus on the materials the product is constructed from.

Use at most 50 words.

Technical specifications: ```{fact_sheet_chair}```
"""
response = get_completion(prompt)
print(response)
```

    This mid-century inspired office chair features a cast aluminum shell with a modified nylon PA6/PA66 coating and a 5-wheel plastic-coated aluminum base. Available in various shell colors and base finishes, it offers options for plastic or full upholstery, and two seat foam densities. Made in Italy.



```python
prompt = f"""
Your task is to help a marketing team create a 
description for a retail website of a product based 
on a technical fact sheet.

Write a product description based on the information 
provided in the technical specifications delimited by 
triple backticks.

The description is intended for furniture retailers, 
so should be technical in nature and focus on the 
materials the product is constructed from.

At the end of the description, include every 7-character 
Product ID in the technical specification.

Use at most 50 words.

Technical specifications: ```{fact_sheet_chair}```
"""
response = get_completion(prompt)
print(response)
```

    This mid-century inspired office chair features a durable 5-wheel plastic-coated aluminum base and a cast aluminum shell with a modified nylon PA6/PA66 coating. Available in various shell colors and base finishes, it offers options for upholstery, armrests, and caster types. Made in Italy. Product IDs: SWC-100, SWC-110.


## Issue 3. Description needs a table of dimensions
- Ask it to extract information and organize it in a table.


```python
prompt = f"""
Your task is to help a marketing team create a 
description for a retail website of a product based 
on a technical fact sheet.

Write a product description based on the information 
provided in the technical specifications delimited by 
triple backticks.

The description is intended for furniture retailers, 
so should be technical in nature and focus on the 
materials the product is constructed from. Use at most 50 words.

At the end of the description, include every 7-character 
Product ID in the technical specification.

After the description, include a table that gives the 
product's dimensions. The table should have two columns.
In the first column include the name of the dimension. 
In the second column include the measurements in inches only.

Give the table the title 'Product Dimensions'.

Format everything as HTML so that can be used in a website. 
Place the description in a <div> element.

Technical specifications: ```{fact_sheet_chair}```
"""

response = get_completion(prompt)
print(response)
```

    ```html
    <div>
        This mid-century inspired office chair features a 5-wheel plastic coated aluminum base and a pneumatic adjuster for easy height adjustment. Constructed with a cast aluminum shell and HD36 foam seat, it offers durability and comfort. Available in multiple finishes and upholstery options. Product IDs: SWC-100, SWC-110.
    </div>
    
    <table title="Product Dimensions">
        <tr>
            <th>Dimension</th>
            <th>Measurement (inches)</th>
        </tr>
        <tr>
            <td>Width</td>
            <td>20.87”</td>
        </tr>
        <tr>
            <td>Depth</td>
            <td>20.08”</td>
        </tr>
        <tr>
            <td>Height</td>
            <td>31.50”</td>
        </tr>
        <tr>
            <td>Seat Height</td>
            <td>17.32”</td>
        </tr>
        <tr>
            <td>Seat Depth</td>
            <td>16.14”</td>
        </tr>
    </table>
    ```


## Load Python libraries to view HTML


```python
from IPython.display import display, HTML
```


```python
display(HTML(response))
```


```html
<div>
    This mid-century inspired office chair features a 5-wheel plastic coated aluminum base and a pneumatic adjuster for easy height adjustment. Constructed with a cast aluminum shell and HD36 foam seat, it offers durability and comfort. Available in multiple finishes and upholstery options. Product IDs: SWC-100, SWC-110.
</div>

<table title="Product Dimensions">
    <tr>
        <th>Dimension</th>
        <th>Measurement (inches)</th>
    </tr>
    <tr>
        <td>Width</td>
        <td>20.87”</td>
    </tr>
    <tr>
        <td>Depth</td>
        <td>20.08”</td>
    </tr>
    <tr>
        <td>Height</td>
        <td>31.50”</td>
    </tr>
    <tr>
        <td>Seat Height</td>
        <td>17.32”</td>
    </tr>
    <tr>
        <td>Seat Depth</td>
        <td>16.14”</td>
    </tr>
</table>
```


## Try experimenting on your own!


```python

```
