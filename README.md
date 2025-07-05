# All About LLMs

A comprehensive learning repository covering Large Language Models (LLMs), AI application development, and prompt engineering techniques.

## Repository Structure

### 📚 Learning Materials

#### **GenAI for Everyone**
- Course materials and notes on generative AI fundamentals
- Video lectures and transcripts organized by week
- Key concepts: LLM capabilities, limitations, prompting techniques
- Covers token concepts, hallucinations, and practical applications

#### **Prompt Engineering Course**
- Structured learning modules covering:
  - Guidelines for effective prompting
  - Iterative prompt development
  - Summarizing and inferring techniques
  - Text transformation methods
  - Chatbot development

### 🛠️ Practical Projects

#### **AI App Development**
Hands-on projects using OpenAI APIs, organized into three modules:

##### 1. **explore-openai/basics/**
- Core OpenAI API fundamentals
- Progressive examples from basic LLM interactions to advanced features
- Topics: text completion, streaming, image generation, audio processing, vision

##### 2. **explore-openai/prompt-engineering/**
- Advanced prompting techniques
- Structured outputs using Pydantic models
- Function calling and external API integration
- Weather and stock data integration examples

##### 3. **explore-openai/streamlit-chat-ui/**
- Interactive web applications using Streamlit
- Chat persistence with SQLite
- Multimodal support (text, images, audio, canvas)
- Progressive complexity from simple chat to full-featured applications

#### **GitHub Copilot**
- Practical exercises with AI-powered code completion
- Starter applications and task configurations

#### **Time Management**
- AI-powered productivity tools and techniques
- Time tracking and optimization strategies

## Technologies Used

- **Python 3.12+**
- **OpenAI API** - GPT-4, DALL-E, Whisper
- **Streamlit** - Web UI framework
- **Poetry** - Dependency management
- **SQLite** - Data persistence
- **Pydantic** - Data validation and modeling
- **Jupyter Notebooks** - Interactive learning

## Getting Started

### Prerequisites
- Python 3.12 or higher
- OpenAI API key
- Poetry package manager

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd All-about-LLMs
   ```

2. **Set up environment variables**
   Create a `.env` file in each project directory:
   ```
   OPENAI_API_KEY=your_api_key_here
   OPEN_AI_MODEL=gpt-4o
   ```

3. **Install dependencies for specific modules**
   ```bash
   # For basics module
   cd "AI App Development/explore-openai/basics"
   poetry install
   poetry shell

   # For prompt engineering
   cd "../prompt-engineering"
   poetry install
   poetry shell

   # For Streamlit chat UI
   cd "../streamlit-chat-ui"
   poetry install
   poetry shell
   ```

### Running Examples

#### Basic OpenAI Examples
```bash
cd "AI App Development/explore-openai/basics"
python src/01_hello_llm.py
```

#### Prompt Engineering Examples
```bash
cd "AI App Development/explore-openai/prompt-engineering"
python src/09_pydantic.py
```

#### Streamlit Chat Applications
```bash
cd "AI App Development/explore-openai/streamlit-chat-ui"
streamlit run src/1_app.py
```

## Learning Path

1. **Start with Theory**: Review GenAI for Everyone materials
2. **Practice Fundamentals**: Work through the basics module examples
3. **Advanced Techniques**: Explore prompt engineering concepts
4. **Build Applications**: Create interactive UIs with Streamlit
5. **Integrate Tools**: Experiment with GitHub Copilot

## Key Features Demonstrated

- **Text Generation**: Chat completions, streaming responses
- **Image Processing**: DALL-E generation, image variations and edits
- **Audio Processing**: Text-to-speech, transcription, translation
- **Vision Capabilities**: Image analysis and understanding
- **Structured Outputs**: JSON schema validation with Pydantic
- **Function Calling**: External API integrations
- **Multimodal Interfaces**: Combined text, image, and audio processing
- **Data Persistence**: SQLite database integration
- **Real-time Features**: Live streaming and interactive components

## File Organization

- `*_final.py` - Complete implementations
- `src/resources/` - Audio, image, and data files
- `model/` - Pydantic data models
- `tools/` - External API integration utilities
- `db/` - Database models and operations

## Contributing

This repository serves as a personal learning collection. Each module is self-contained and can be explored independently based on your learning objectives.

## License

This project is for educational purposes and personal learning.