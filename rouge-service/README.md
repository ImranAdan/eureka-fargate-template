# 🥷 Rogue Service (Python)

**Rogue Service** is a FastAPI-based microservice that represents a rogue-type bot in the "Battle of the Bots" tournament system. It integrates into a Spring Cloud ecosystem using Spring Cloud Sidecar and retrieves its battle configuration dynamically from a Spring Cloud Config Server.

---

## 📦 Features

- REST endpoint to **spawn a rogue bot instance** with randomized name and config-defined stats.
- `/health.json` endpoint for Spring Cloud Sidecar compatibility.
- Dynamically loads configuration from a Spring Cloud Config Server (Git-backed).
- Designed to be **polyglot-compatible** with Spring Boot via Eureka + Feign.

---

## 🚀 How to Run

### 1. Install Dependencies

```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

### 2. Set Required Environment Variables

```bash
export CONFIG_SERVER_URL=${CONFIG_SERVER} # URI of config-server or can default to localhost:8888
export SPRING_APPLICATION_NAME=${SERVICE_NAME}
export SPRING_PROFILES_ACTIVE=${PROFILE}
```

### 3. Start the Service
```bash
uvicorn src.main:app --host 0.0.0.0 --port 8000
```
---

## 🧪 API Endpoints

`GET /spawn`

Returns a unique rogue bot instance.

Example Response:

```bash
{
  "name": "Shadowfang",
  "type": "Rogue",
  "hp": 100,
  "attack": 25,
  "defense": 10,
  "speed": 18,
  "critChance": 0.2,
  "dodgeChance": 0.25
}
```

`GET /health.json`

Health check used by Spring Cloud Sidecar.

Response:

```json
{ "status": "UP" }
```

---

## ⚙️ Config Format (RogueService.yml in Git repo)

This is an example of a config being retried, this would usually be in version control. 

```yml
bot:
  type: Rogue
  hp: 100
  attack: 25
  defense: 10
  speed: 18
  critChance: 0.2
  dodgeChance: 0.25
```

---

## 🧪 Running Tests

```bash
pytest
```
