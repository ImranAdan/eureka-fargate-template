import os
import requests
import threading
import time

EUREKA_URL = os.getenv("EUREKA_SERVER", "http://localhost:8761/eureka")
APP_NAME = os.getenv("SPRING_APPLICATION_NAME", "rogue-service")
PORT = int(os.getenv("PORT", 3000))
HOSTNAME = os.getenv("EUREKA_INSTANCE_HOSTNAME", "localhost")
INSTANCE_ID = f"{HOSTNAME}:{APP_NAME}:{PORT}"

def register_with_eureka(max_retries=10, retry_interval=5):
    instance_info = {
        "instance": {
            "instanceId": INSTANCE_ID,
            "hostName": HOSTNAME,
            "app": APP_NAME.upper(),
            "ipAddr": HOSTNAME,
            "status": "UP",
            "port": { "$": PORT, "@enabled": True },
            "dataCenterInfo": {
                "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                "name": "MyOwn"
            },
            "healthCheckUrl": f"http://{HOSTNAME}:{PORT}/health.json",
            "statusPageUrl": f"http://{HOSTNAME}:{PORT}/health.json",
            "homePageUrl": f"http://{HOSTNAME}:{PORT}"
        }
    }

    for attempt in range(max_retries):
        try:
            print(f"Registering {APP_NAME} with Eureka at {EUREKA_URL} (attempt {attempt + 1})")
            res = requests.post(
                f"{EUREKA_URL}/apps/{APP_NAME}",
                json=instance_info,
                headers={"Content-Type": "application/json"},
                timeout=5
            )
            print("Eureka registration response:", res.status_code)
            if res.status_code == 204:
                return
            else:
                print("Unexpected response, retrying...")
        except Exception as e:
            print("Failed to register with Eureka:", e)

        time.sleep(retry_interval)

    print("❌ Gave up trying to register with Eureka.")


def start_heartbeat():
    def beat():
        while True:
            try:
                res = requests.put(
                    f"{EUREKA_URL}/apps/{APP_NAME}/{INSTANCE_ID}",
                    headers={"Content-Type": "application/json"}
                )
                print("Heartbeat sent:", res.status_code)
            except Exception as e:
                print("Eureka heartbeat failed:", e)
            time.sleep(30)

    thread = threading.Thread(target=beat, daemon=True)
    thread.start()
