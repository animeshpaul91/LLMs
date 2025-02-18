# The Chat Format

In this notebook, you will explore how you can utilize the chat format to have extended conversations with chatbots personalized or specialized for specific tasks or behaviors.

## Setup


```python
import os
import openai
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

def get_completion_from_messages(messages, model="gpt-4o", temperature=0):
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=temperature, # this is the degree of randomness of the model's output
    )
    #print(str(response.choices[0].message))
    return response.choices[0].message["content"]
```


```python
messages =  [  
{'role':'system', 'content':'You are an assistant that speaks like Shakespeare.'},    
{'role':'user', 'content':'tell me a joke'},   
{'role':'assistant', 'content':'Why did the chicken cross the road'},   
{'role':'user', 'content':'I don\'t know'}  ]
```


```python
response = get_completion_from_messages(messages, temperature=1)
print(response)
```

    To reach the other side—with haste, by my troth!



```python
messages =  [  
{'role':'system', 'content':'You are friendly chatbot.'},    
{'role':'user', 'content':'Hi, my name is Isa'}  ]
response = get_completion_from_messages(messages, temperature=1)
print(response)
```

    Hi Isa! It's great to meet you. How can I assist you today?



```python
messages =  [  
{'role':'system', 'content':'You are friendly chatbot.'},    
{'role':'user', 'content':'Yes,  can you remind me, What is my name?'}  ]
response = get_completion_from_messages(messages, temperature=1)
print(response)
```

    I'm sorry, but I don't have access to personal information like your name. If you'd like, you can tell me your name and I'll remember it for this conversation!



```python
messages =  [  
{'role':'system', 'content':'You are friendly chatbot.'},
{'role':'user', 'content':'Hi, my name is Isa'},
{'role':'assistant', 'content': "Hi Isa! It's nice to meet you. \
Is there anything I can help you with today?"},
{'role':'user', 'content':'Yes, you can remind me, What is my name?'}  ]
response = get_completion_from_messages(messages, temperature=1)
print(response)
```

    Your name is Isa! If there's anything else you'd like to know or discuss, feel free to let me know.


# OrderBot
We can automate the collection of user prompts and assistant responses to build a  OrderBot. The OrderBot will take orders at a pizza restaurant. 


```python
def collect_messages(_):
    prompt = inp.value_input
    inp.value = ''
    context.append({'role':'user', 'content':f"{prompt}"})
    response = get_completion_from_messages(context) 
    
    # Appending to Context
    context.append({'role':'assistant', 'content':f"{response}"})
    panels.append(
        pn.Row('User:', pn.pane.Markdown(prompt, width=600)))
    panels.append(
        pn.Row('Assistant:', pn.pane.Markdown(response, width=600, style={'background-color': '#F6F6F6'})))
 
    return pn.Column(*panels)

```


```python
import panel as pn  # GUI
pn.extension()

panels = [] # collect display 

context = [ {'role':'system', 'content':"""
You are OrderBot, an automated service to collect orders for a pizza restaurant. \
You first greet the customer, then collects the order, \
and then asks if it's a pickup or delivery. \
You wait to collect the entire order, then summarize it and check for a final \
time if the customer wants to add anything else. \
If it's a delivery, you ask for an address. \
Finally you collect the payment.\
Make sure to clarify all options, extras and sizes to uniquely \
identify the item from the menu.\
You respond in a short, very conversational friendly style. \
The menu includes \
pepperoni pizza  12.95, 10.00, 7.00 \
cheese pizza   10.95, 9.25, 6.50 \
eggplant pizza   11.95, 9.75, 6.75 \
fries 4.50, 3.50 \
greek salad 7.25 \
Toppings: \
extra cheese 2.00, \
mushrooms 1.50 \
sausage 3.00 \
canadian bacon 3.50 \
AI sauce 1.50 \
peppers 1.00 \
Drinks: \
coke 3.00, 2.00, 1.00 \
sprite 3.00, 2.00, 1.00 \
bottled water 5.00 \
"""} ]  # accumulate messages


inp = pn.widgets.TextInput(value="Hi", placeholder='Enter text here…')
button_conversation = pn.widgets.Button(name="Chat!")

interactive_conversation = pn.bind(collect_messages, button_conversation)

dashboard = pn.Column(
    inp,
    pn.Row(button_conversation),
    pn.panel(interactive_conversation, loading_indicator=True, height=300),
)

dashboard
```






<style>.bk-root, .bk-root .bk:before, .bk-root .bk:after {
  font-family: var(--jp-ui-font-size1);
  font-size: var(--jp-ui-font-size1);
  color: var(--jp-ui-font-color1);
}
</style>







<div id='1002'>
  <div class="bk-root" id="7007070d-60bb-49a7-a9d1-1c936c28cb07" data-root-id="1002"></div>
</div>
<script type="application/javascript">(function(root) {
  function embed_document(root) {
    var docs_json = {"557536fc-196f-4b03-b909-33e077026752":{"defs":[{"extends":null,"module":null,"name":"ReactiveHTML1","overrides":[],"properties":[]},{"extends":null,"module":null,"name":"FlexBox1","overrides":[],"properties":[{"default":"flex-start","kind":null,"name":"align_content"},{"default":"flex-start","kind":null,"name":"align_items"},{"default":"row","kind":null,"name":"flex_direction"},{"default":"wrap","kind":null,"name":"flex_wrap"},{"default":"flex-start","kind":null,"name":"justify_content"}]},{"extends":null,"module":null,"name":"GridStack1","overrides":[],"properties":[{"default":"warn","kind":null,"name":"mode"},{"default":null,"kind":null,"name":"ncols"},{"default":null,"kind":null,"name":"nrows"},{"default":true,"kind":null,"name":"allow_resize"},{"default":true,"kind":null,"name":"allow_drag"},{"default":[],"kind":null,"name":"state"}]},{"extends":null,"module":null,"name":"click1","overrides":[],"properties":[{"default":"","kind":null,"name":"terminal_output"},{"default":"","kind":null,"name":"debug_name"},{"default":0,"kind":null,"name":"clears"}]},{"extends":null,"module":null,"name":"NotificationAreaBase1","overrides":[],"properties":[{"default":"bottom-right","kind":null,"name":"position"},{"default":0,"kind":null,"name":"_clear"}]},{"extends":null,"module":null,"name":"NotificationArea1","overrides":[],"properties":[{"default":[],"kind":null,"name":"notifications"},{"default":"bottom-right","kind":null,"name":"position"},{"default":0,"kind":null,"name":"_clear"},{"default":[{"background":"#ffc107","icon":{"className":"fas fa-exclamation-triangle","color":"white","tagName":"i"},"type":"warning"},{"background":"#007bff","icon":{"className":"fas fa-info-circle","color":"white","tagName":"i"},"type":"info"}],"kind":null,"name":"types"}]},{"extends":null,"module":null,"name":"Notification","overrides":[],"properties":[{"default":null,"kind":null,"name":"background"},{"default":3000,"kind":null,"name":"duration"},{"default":null,"kind":null,"name":"icon"},{"default":"","kind":null,"name":"message"},{"default":null,"kind":null,"name":"notification_type"},{"default":false,"kind":null,"name":"_destroyed"}]},{"extends":null,"module":null,"name":"TemplateActions1","overrides":[],"properties":[{"default":0,"kind":null,"name":"open_modal"},{"default":0,"kind":null,"name":"close_modal"}]},{"extends":null,"module":null,"name":"MaterialTemplateActions1","overrides":[],"properties":[{"default":0,"kind":null,"name":"open_modal"},{"default":0,"kind":null,"name":"close_modal"}]}],"roots":{"references":[{"attributes":{"reload":false},"id":"1016","type":"panel.models.location.Location"},{"attributes":{"children":[{"id":"1003"},{"id":"1004"},{"id":"1006"}],"margin":[0,0,0,0],"name":"Column00122"},"id":"1002","type":"Column"},{"attributes":{"args":{"bidirectional":false,"properties":{"event:button_click":"loading"},"source":{"id":"1005"},"target":{"id":"1006"}},"code":"\n    if ('event:button_click'.startsWith('event:')) {\n      var value = true\n    } else {\n      var value = source['event:button_click'];\n      value = value;\n    }\n    if (typeof value !== 'boolean' || source.labels !== ['Loading']) {\n      value = true\n    }\n    var css_classes = target.css_classes.slice()\n    var loading_css = ['pn-loading', 'arc']\n    if (value) {\n      for (var css of loading_css) {\n        if (!(css in css_classes)) {\n          css_classes.push(css)\n        }\n      }\n    } else {\n     for (var css of loading_css) {\n        var index = css_classes.indexOf(css)\n        if (index > -1) {\n          css_classes.splice(index, 1)\n        }\n      }\n    }\n    target['css_classes'] = css_classes\n    ","tags":[[139647297981840,[null,"event:button_click"],[null,"loading"]]]},"id":"1014","type":"CustomJS"},{"attributes":{"css_classes":["markdown"],"margin":[5,5,5,5],"name":"Markdown00115","style":{"background-color":"#F6F6F6"},"text":"&lt;p&gt;Hello! Welcome to our pizza place. What deliciousness can I get started for you today? \ud83c\udf55\ud83d\ude0a&lt;/p&gt;","width":600},"id":"1013","type":"panel.models.markup.HTML"},{"attributes":{"css_classes":["markdown"],"margin":[5,5,5,5],"name":"Markdown00117","text":"&lt;p&gt;Assistant:&lt;/p&gt;"},"id":"1012","type":"panel.models.markup.HTML"},{"attributes":{"css_classes":["markdown"],"margin":[5,5,5,5],"name":"Markdown00110","width":600},"id":"1010","type":"panel.models.markup.HTML"},{"attributes":{"margin":[5,10,5,10],"max_length":5000,"placeholder":"Enter text here\u2026"},"id":"1003","type":"TextInput"},{"attributes":{"children":[{"id":"1005"}],"margin":[0,0,0,0],"name":"Row00103"},"id":"1004","type":"Row"},{"attributes":{"children":[{"id":"1008"},{"id":"1011"}],"margin":[0,0,0,0],"name":"Column00120"},"id":"1007","type":"Column"},{"attributes":{"icon":null,"js_event_callbacks":{"button_click":[{"id":"1014"}]},"label":"Chat!","margin":[5,10,5,10],"subscribed_events":["button_click"]},"id":"1005","type":"Button"},{"attributes":{"css_classes":["markdown"],"margin":[5,5,5,5],"name":"Markdown00112","text":"&lt;p&gt;User:&lt;/p&gt;"},"id":"1009","type":"panel.models.markup.HTML"},{"attributes":{"children":[{"id":"1012"},{"id":"1013"}],"margin":[0,0,0,0],"name":"Row00119"},"id":"1011","type":"Row"},{"attributes":{"children":[{"id":"1009"},{"id":"1010"}],"margin":[0,0,0,0],"name":"Row00114"},"id":"1008","type":"Row"},{"attributes":{"client_comm_id":"0365564ca90d484484ee4b652def7a18","comm_id":"287b90665a2d4590b2c1c73c0620693d","plot_id":"1002"},"id":"1015","type":"panel.models.comm_manager.CommManager"},{"attributes":{"children":[{"id":"1007"}],"height":300,"margin":[0,0,0,0],"min_height":300,"name":"Row00108"},"id":"1006","type":"Row"}],"root_ids":["1002","1015","1016"]},"title":"Bokeh Application","version":"2.4.3"}};
    var render_items = [{"docid":"557536fc-196f-4b03-b909-33e077026752","root_ids":["1002"],"roots":{"1002":"7007070d-60bb-49a7-a9d1-1c936c28cb07"}}];
    root.Bokeh.embed.embed_items_notebook(docs_json, render_items);
    for (const render_item of render_items) {
      for (const root_id of render_item.root_ids) {
	const id_el = document.getElementById(root_id)
	if (id_el.children.length && (id_el.children[0].className === 'bk-root')) {
	  const root_el = id_el.children[0]
	  root_el.id = root_el.id + '-rendered'
	}
      }
    }
  }
  if (root.Bokeh !== undefined && root.Bokeh.Panel !== undefined) {
    embed_document(root);
  } else {
    var attempts = 0;
    var timer = setInterval(function(root) {
      if (root.Bokeh !== undefined && root.Bokeh.Panel !== undefined) {
        clearInterval(timer);
        embed_document(root);
      } else if (document.readyState == "complete") {
        attempts++;
        if (attempts > 200) {
          clearInterval(timer);
          console.log("Bokeh: ERROR: Unable to run BokehJS code because BokehJS library is missing");
        }
      }
    }, 25, root)
  }
})(window);</script>




```python
messages =  context.copy() # deep-copy
messages.append(
{'role':'system', 'content':'create a json summary of the previous food order. Itemize the price for each item\
 The fields should be 1) pizza, include size 2) list of toppings 3) list of drinks, include size   4) list of sides include size  5)total price '},    
)
 #The fields should be 1) pizza, price 2) list of toppings 3) list of drinks, include size include price  4) list of sides include size include price, 5)total price '},    

response = get_completion_from_messages(messages, temperature=0)
print(response)
```

    ```json
    {
      "pizza": {
        "type": "Pepperoni",
        "size": "Large",
        "price": 12.95
      },
      "toppings": [
        {
          "type": "Mushrooms",
          "price": 1.50
        }
      ],
      "drinks": [
        {
          "type": "Coke",
          "size": "Medium",
          "price": 2.00
        },
        {
          "type": "Bottled Water",
          "price": 5.00
        }
      ],
      "sides": [
        {
          "type": "Fries",
          "size": "Small",
          "price": 3.50
        }
      ],
      "total_price": 28.95
    }
    ```


## Try experimenting on your own!

You can modify the menu or instructions to create your own orderbot!


```python

```
