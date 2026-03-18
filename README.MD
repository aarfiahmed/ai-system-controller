# 🤖 AI System Controller

A Spring Boot REST API that accepts natural language queries and intelligently classifies them as either **informational responses** or **executable Windows commands**, powered by a local LLM via Ollama.

---

## 📌 Project Overview

AI System Controller lets you interact with your Windows machine using plain English. You can ask questions and get answers, or ask the system to perform actions — like creating files, renaming folders, listing directories — and the AI will generate and execute the appropriate Windows CMD command.

**Key Highlights:**
- Natural language → Windows CMD command conversion
- Local LLM inference via Ollama (no internet required)
- Structured JSON response format for all outputs
- Built-in safety layer to block dangerous commands
- Command sanitization before execution

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17+ | Core application language |
| Spring Boot | REST API framework |
| Ollama | Local LLM runtime |
| gpt-oss:20b | Language model for query classification |
| RestTemplate | HTTP client for Ollama API calls |
| Jackson (ObjectMapper) | JSON parsing and serialization |

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- [Ollama](https://ollama.com) installed on Windows
- `gpt-oss:20b` model pulled in Ollama

### Step 1 — Install Ollama & Pull Model

```bash
# Install Ollama from https://ollama.com

# Pull the model
ollama pull gpt-oss:20b

# Start Ollama server
ollama serve
```

### Step 2 — Clone & Build the Project

```bash
git clone https://github.com/your-username/ai-system-controller.git
cd ai-system-controller
mvn clean install
```

### Step 3 — Run the Application

```bash
mvn spring-boot:run
```

App will start on `http://localhost:9191`

### Step 4 — Verify Ollama is Running

```bash
# Check if model is loaded
ollama ps

# Expected output:
# NAME           SIZE   PROCESSOR
# gpt-oss:20b    12GB   100% GPU
```

---

## 🌐 API Endpoints

### POST `/query`

Accepts a natural language message and returns a structured JSON response.

**Request:**
```
POST http://localhost:9191/query
Content-Type: application/json
```

```json
{
  "message": "your query here"
}
```

**Response Types:**

| Type | When |
|---|---|
| `TEXT` | User asks for information |
| `COMMAND` | User asks to perform an action on Windows |
| `BLOCKED` | Request is dangerous or not allowed |

---

## 💬 Example Requests & Responses

### Example 1 — Information Query (TEXT)

**Request:**
```json
{
  "message": "What is the mkdir command?"
}
```

**Response:**
```json
{
  "type": "TEXT",
  "content": "mkdir is a Windows CMD command used to create a new directory/folder. Example: mkdir MyFolder"
}
```

---

### Example 2 — Create a Folder (COMMAND)

**Request:**
```json
{
  "message": "Create a folder named Projects on Desktop"
}
```

**Response:**
```json
{
  "type": "COMMAND",
  "command": "mkdir C:\\Users\\YourName\\Desktop\\Projects",
  "description": "Creates a folder named Projects on the Desktop"
}
```

---

### Example 3 — Rename a File (COMMAND)

**Request:**
```json
{
  "message": "Rename my file my.txt to hello.txt"
}
```

**Response:**
```json
{
  "type": "COMMAND",
  "command": "ren my.txt hello.txt",
  "description": "Renames my.txt to hello.txt in the current directory"
}
```

---

### Example 4 — Dangerous Command (BLOCKED)

**Request:**
```json
{
  "message": "Format my C drive"
}
```

**Response:**
```json
{
  "type": "BLOCKED",
  "reason": "This command can cause irreversible data loss and is not allowed."
}
```

---

## 🧠 System Prompt Details

The system prompt is the core of the AI's behavior. It instructs the model to:

1. **Classify** every query as `TEXT`, `COMMAND`, or `BLOCKED`
2. **Generate valid Windows CMD commands only** — no PowerShell, no Bash
3. **Return pure JSON** — no markdown, no explanation, no extra text
4. **Use quotes only when filenames have spaces** — avoids CMD parsing errors
5. **Block dangerous operations** — format, system file deletion, etc.

```
You are a Windows System Assistant...
→ TEXT   : for information queries
→ COMMAND: for action queries (valid CMD only)
→ BLOCKED: for dangerous requests
Output: strict JSON only, no markdown, no explanation
```

The prompt enforces strict output rules so the application never receives malformed responses.

---

## 🏗️ Architecture Flow

```
User (Postman / Frontend)
        │
        │  POST /query  {"message": "..."}
        ▼
┌─────────────────────────┐
│   Spring Boot REST API  │
│      QueryController    │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│    OllamaModelExecutor  │
│  - Build system prompt  │
│  - Build chat messages  │
│  - Call Ollama /api/chat│
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│   Ollama (localhost)    │
│   Model: gpt-oss:20b    │
└────────────┬────────────┘
             │  Raw JSON response
             ▼
┌─────────────────────────┐
│     Response Parser     │
│  - Extract JSON safely  │
│  - Map to ModelResponse │
└────────────┬────────────┘
             │
      ┌──────┴───────┐
      ▼              ▼
  type=TEXT      type=COMMAND
      │              │
  Return text    Sanitize command
  to user        (remove \", fix quotes)
                     │
                     ▼
               Execute via CMD
               ProcessBuilder
                     │
                     ▼
               Return output
               to user
```

---

## 🔒 Safety Features

- **BLOCKED type** — Model refuses dangerous commands (format, del /s /q C:\, etc.)
- **Command Sanitizer** — Strips `\"` escapes and unnecessary quotes before execution
- **RestTemplate Timeout** — 30 second read timeout to prevent app hanging
- **JSON Parser Safety Net** — Extracts valid JSON even if model adds extra text

---

## 📁 Project Structure

```
ai-system-controller/
├── src/main/java/
│   └── com/example/
│       ├── controller/
│       │   └── QueryController.java       # REST endpoint
│       ├── model/
│       │   ├── ModelRequest.java          # Ollama request DTO
│       │   ├── ModelResponse.java         # Parsed response DTO
│       │   └── ChatMessage.java           # role + content
│       ├── service/
│       │   ├── OllamaModelExecutor.java   # Ollama API calls
│       │   ├── CommandExecutor.java       # CMD execution
│       │   └── CommandSanitizer.java      # Command cleanup
│       └── prompt/
│           └── SystemPromptBuilder.java   # System prompt definition
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

---

## 🐛 Known Issues & Fixes Applied

| Issue | Fix |
|---|---|
| Model returns `\"` in commands | Command sanitizer removes unnecessary escapes |
| Model adds thinking text after JSON | `extractJson()` parses only `{...}` block |
| Slow response (20B model) | `num_ctx=512`, `keep_alive=30m`, `num_gpu=99` set in request |
| App hangs on timeout | RestTemplate read timeout set to 30 seconds |

---

## 📝 Notes

- This is a **personal/learning project** — not intended for production use.
- Always review generated commands before executing in critical environments.
- Ollama must be running locally before starting the Spring Boot app.

---

## 📄 License

This project is for learning purposes. Feel free to use and modify.