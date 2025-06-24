# tests/test_health.py
from fastapi.testclient import TestClient
from main import app  # if PYTHONPATH includes src/

client = TestClient(app)

def test_health_check():
    response = client.get("/health.json")
    assert response.status_code == 200
    assert response.json() == {"status": "UP"}
