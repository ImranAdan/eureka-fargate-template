from fastapi import FastAPI
from pydantic import BaseModel
from faker import Faker
import requests
import os
from eureka import register_with_eureka, start_heartbeat

app = FastAPI()
faker = Faker()

class BotInstance(BaseModel):
    name: str
    type: str
    hp: int
    attack: int
    defense: int
    speed: int
    critChance: float
    dodgeChance: float

def load_config():
    config_server_url = os.environ.get("CONFIG_SERVER_URL", "http://localhost:8888")
    profile = os.environ.get("SPRING_PROFILES_ACTIVE", "default")
    app_name = os.environ.get("SPRING_APPLICATION_NAME", "rogue-service")
    
    url = f"{config_server_url}/{app_name}/{profile}"
    print(f"Fetching config from: {url}")

    try:
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()
        property_sources = data.get("propertySources", [])

        config = {}
        for source in property_sources:
            props = source.get("source", {})
            for key, value in props.items():
                if key.startswith("bot."):
                    config[key[4:]] = value

        if not config:
            raise ValueError("No bot.* keys found in config")

        return config
    except Exception as e:
        print("Failed to load config from Spring Config Server:", e)
        return None

@app.get("/spawn", response_model=BotInstance)
def spawn_rogue():
    ROGUE_CONFIG = load_config()
    if ROGUE_CONFIG is None:
        raise RuntimeError("Failed to load rogue config")
    return BotInstance(
        name=faker.first_name(),
        type=ROGUE_CONFIG["type"],
        hp=ROGUE_CONFIG["hp"],
        attack=ROGUE_CONFIG["attack"],
        defense=ROGUE_CONFIG["defense"],
        speed=ROGUE_CONFIG["speed"],
        critChance=ROGUE_CONFIG["critChance"],
        dodgeChance=ROGUE_CONFIG["dodgeChance"]
    )

@app.get("/health.json")
def health_check():
    return {"status": "UP"}

if __name__ == "__main__":
    register_with_eureka()
    start_heartbeat()

    import uvicorn
    import socket
    HOSTNAME = os.getenv("EUREKA_INSTANCE_HOSTNAME", socket.gethostname())
    PORT = int(os.getenv("PORT", 3000))
    uvicorn.run(app, host="0.0.0.0", port=PORT)
