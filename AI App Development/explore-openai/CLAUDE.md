# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

This repository contains three independent Poetry-based Python modules for exploring OpenAI APIs:

### 1. `basics/` - Core OpenAI API Fundamentals
- **Purpose**: Basic OpenAI API interactions and features
- **Key Files**: Numbered progression from `01_hello_llm.py` to `11_audio_translate.py`
- **Dependencies**: `openai`, `python-dotenv`, `requests`
- **Setup**: `cd basics && poetry install && poetry shell`
- **Run Examples**: `python src/01_hello_llm.py`

### 2. `prompt-engineering/` - Advanced Prompting Techniques
- **Purpose**: Structured outputs, function calling, and prompt optimization
- **Key Files**: Numbered progression with Pydantic models in `model/` directory
- **Dependencies**: `openai`, `python-dotenv`, `pydantic`, `requests`
- **Setup**: `cd prompt-engineering && poetry install && poetry shell`
- **Run Examples**: `python src/09_pydantic.py`

### 3. `streamlit-chat-ui/` - Interactive Web Applications
- **Purpose**: Chat UIs with persistence, multimodal support, and real-time features
- **Key Files**: Progressive Streamlit apps with SQLite persistence
- **Dependencies**: `streamlit`, `openai`, `pydantic`, `pyttsx3`, `yfinance`, `langchain-openai`
- **Setup**: `cd streamlit-chat-ui && poetry install && poetry shell`
- **Run Apps**: `streamlit run src/1_app.py`

## Environment Configuration

Each module requires a `.env` file with:
```
OPENAI_API_KEY=your_api_key_here
OPEN_AI_MODEL=gpt-4o  # or gpt-4o-mini
```

## Common Development Patterns

### OpenAI Client Initialization
```python
from openai import OpenAI
import os
from dotenv import load_dotenv

load_dotenv()
client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))
model = os.getenv("OPEN_AI_MODEL")
```

### Message Structure
Standard pattern across all modules:
```python
messages = [
    {"role": "system", "content": "System prompt here"},
    {"role": "user", "content": "User input here"}
]
```

### File Naming Convention
- Base implementations: `XX_feature_name.py`
- Complete implementations: `XX_feature_name_final.py`
- Numbered progression indicates learning sequence

## Module-Specific Architecture

### Basics Module
- Linear progression through OpenAI features
- Direct API calls without abstractions
- Resource files in `src/resources/`

### Prompt Engineering Module
- Pydantic models in `model/` directory for structured outputs
- External API integrations (weather, stock data) in `tools/`
- JSON example data in `resources/`
- Function calling implementations

### Streamlit Chat UI Module
- SQLite database models in `db/chat_db.py`
- Pydantic models in `model/chats_models.py`
- Session state management for chat persistence
- Multimodal support (images, audio, canvas)
- Progressive complexity from simple chat to full-featured applications

## Development Commands

### Install Dependencies (per module)
```bash
cd [module-name]
poetry install
poetry shell
```

### Run Examples
```bash
# Basics and prompt-engineering
python src/[filename].py

# Streamlit apps
streamlit run src/[filename].py
```

### Database Operations (streamlit-chat-ui only)
SQLite database automatically created in module directory. Database operations handled through `db/chat_db.py`.

## Key Features Implemented

- **Basic API Usage**: Text completion, streaming, parameters
- **Image Generation**: DALL-E integration, variations, edits
- **Audio Processing**: Text-to-speech, transcription, translation
- **Vision**: Image analysis and encoded image processing
- **Structured Outputs**: Pydantic models, response formats
- **Function Calling**: External API integration
- **Chat Persistence**: SQLite storage with conversation management
- **Multimodal Chat**: Image upload, audio recording, canvas drawing
- **Real-time Features**: Streaming responses, live updates

## Testing and Validation

No formal test suite. Validation through:
- Running example files in sequence
- Checking API responses and outputs
- Visual inspection of generated content (images, audio files)
- Interactive testing through Streamlit interfaces