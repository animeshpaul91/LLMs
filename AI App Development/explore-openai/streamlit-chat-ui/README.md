# Streamlit Chat UI

Interactive web applications demonstrating OpenAI API integration with Streamlit, featuring chat persistence, multimodal support, and real-time features.

## Overview

This module contains progressive Streamlit applications that showcase various OpenAI API capabilities through interactive web interfaces. Each application builds upon the previous one, adding new features and complexity.

## Features

- **Basic Chat Interface**: Simple OpenAI API integration with Streamlit
- **Session Management**: Persistent chat sessions across page refreshes
- **Canvas Integration**: Interactive drawing and calculation capabilities
- **SQLite Persistence**: Chat history storage and retrieval
- **Multimodal Support**: Image upload, audio recording, and canvas drawing
- **Real-time Features**: Streaming responses and live updates
- **Sidebar Navigation**: Multiple chat management

## Project Structure

```
streamlit-chat-ui/
├── src/
│   ├── 1_app.py                           # Basic chat app
│   ├── 2_app_with_session.py              # Session state management
│   ├── 3_canvas_calculator.py             # Canvas drawing integration
│   ├── 4_chatbot-sidebar.py               # Multi-chat sidebar
│   ├── 5_learn_sqlite3.py                 # SQLite database operations
│   ├── 6_chatbot_persistance.py           # Chat persistence
│   ├── 7_chatbot_multimodal_img.py        # Image multimodal support
│   ├── 8_chatbot_multimodal_img_audio.py  # Audio + image support
│   ├── db/
│   │   └── chat_db.py                     # Database operations
│   ├── model/
│   │   └── chats_models.py                # Pydantic models
│   └── multi-modal-images/                # Additional multimodal examples
├── pyproject.toml                         # Poetry dependencies
└── README.md
```

## Setup

1. **Install Dependencies**
   ```bash
   cd streamlit-chat-ui
   poetry install
   poetry shell
   ```

2. **Environment Configuration**
   Create a `.env` file with:
   ```
   OPENAI_API_KEY=your_api_key_here
   OPEN_AI_MODEL=gpt-4o  # or gpt-4o-mini
   ```

## Running Applications

```bash
# Basic chat application
streamlit run src/1_app.py

# Session-based chat
streamlit run src/2_app_with_session.py

# Canvas calculator
streamlit run src/3_canvas_calculator.py

# Multi-chat with sidebar
streamlit run src/4_chatbot-sidebar.py

# Persistent chat with database
streamlit run src/6_chatbot_persistance.py

# Multimodal chat with images
streamlit run src/7_chatbot_multimodal_img.py

# Full multimodal chat (images + audio)
streamlit run src/8_chatbot_multimodal_img_audio.py
```

## Application Progression

### 1. Basic Chat (`1_app.py`)
- Simple OpenAI API integration
- Basic Streamlit interface
- Text-based conversations

### 2. Session Management (`2_app_with_session.py`)
- Persistent chat sessions
- Session state management
- Message history preservation

### 3. Canvas Calculator (`3_canvas_calculator.py`)
- Interactive drawing canvas
- Mathematical calculation from drawings
- Vision API integration

### 4. Sidebar Navigation (`4_chatbot-sidebar.py`)
- Multiple chat sessions
- Sidebar-based navigation
- Enhanced user interface

### 5. Database Learning (`5_learn_sqlite3.py`)
- SQLite database operations
- CRUD operations practice
- Database schema setup

### 6. Persistent Chat (`6_chatbot_persistance.py`)
- Full chat persistence
- Database-backed storage
- Chat history management

### 7. Multimodal Images (`7_chatbot_multimodal_img.py`)
- Image upload support
- Vision API integration
- Image analysis capabilities

### 8. Full Multimodal (`8_chatbot_multimodal_img_audio.py`)
- Complete multimodal experience
- Audio recording and transcription
- Image and audio processing

## Key Components

### Database Models (`db/chat_db.py`)
- SQLite database initialization
- Chat and message CRUD operations
- Database connection management

### Pydantic Models (`model/chats_models.py`)
- Structured data models
- Type validation
- API response formatting

### Dependencies
- **streamlit**: Web application framework
- **openai**: OpenAI API client
- **pydantic**: Data validation
- **pyttsx3**: Text-to-speech
- **yfinance**: Financial data
- **langchain-openai**: LangChain integration

## Database Schema

The SQLite database includes:
- `chats` table: Chat session metadata
- `messages` table: Individual chat messages
- Foreign key relationships for data integrity

## Development Notes

- Each `*_final.py` file contains the complete implementation
- Base files (`*.py`) may be incomplete or work-in-progress
- Applications automatically create `chat_history.db` in the module directory
- All applications support streaming responses where applicable

## Usage Examples

1. **Start Basic Chat**: Run `streamlit run src/1_app.py` for simple text chat
2. **Persistent Sessions**: Use `6_chatbot_persistance.py` for saved conversations
3. **Multimodal Experience**: Try `8_chatbot_multimodal_img_audio.py` for full features
4. **Canvas Drawing**: Use `3_canvas_calculator.py` for interactive math

## File Naming Convention

- `XX_feature_name.py`: Base implementation
- `XX_feature_name_final.py`: Complete implementation
- Numbered progression indicates learning sequence and complexity