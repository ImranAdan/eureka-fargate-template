# 🛡️ Battle Service Microservice

This project implements a self-contained battle simulation microservice as part of your microservices architecture, using Spring Boot, Spring Cloud Config, Eureka, and Docker.

---

## 📦 What This Service Does

✅ Loads bot definitions (types, stats) dynamically from Spring Cloud Config  
✅ Registers itself with Eureka for service discovery  
✅ Generates a randomized tournament with N participants  
✅ Simulates round-by-round single-elimination battles  
✅ Logs detailed turn-by-turn events including critical hits and dodges  
✅ Exposes REST endpoints to interact with the service  
✅ Runs fully containerized alongside Eureka and Config servers in Docker Compose

---

✅ Requirements

* Java 17+
* Maven or Gradle build system
* Spring Boot 3.x
* Docker & Docker Compose
* Eureka and Spring Cloud Config running in your stack

---

## 🔧 How Bots Are Configured

Bots are defined in your config repository under a file like `battle-service.yml`, e.g.:

```yaml
bots:
  types:
    rogue:
      type: rogue
      hp: 100
      attack: 30
      ...
    warrior:
      type: warrior
      hp: 120
      attack: 25
      ...
```

---

## 🚀 Endpoints

### 🎲 `/tournament?participants=8`

* Starts a single-elimination tournament with 8 (or N) randomly generated bots.
* Returns a TournamentResult JSON with the overall winner and a full log of all battles.

```json 
{
  "winner": {
    "name": "Aiden",
    "type": "mage",
    ...
  },
  "battles": [
    {
      "bot1": { "name": "Luna", "type": "rogue", ... },
      "bot2": { "name": "Gorak", "type": "tank", ... },
      "winner": { "name": "Luna", "type": "rogue", ... }
    },
    ...
  ]
}
```

---

## 🏗️ Project Structure

* BotFactory: Generates bots from loaded config.
* BattleResolver: Simulates 1v1 battles (damage, crits, dodges, speed).
* TournamentService: Creates participants, runs matches, advances winners, logs results.
* BattleController: REST API controller for tournament entrypoint.
* Models: Bot, BattleLogEntry, MatchResult, TournamentResult.

---

## ⚙️ Running the Service

Locally with Docker Compose

Make sure your Eureka and Config servers are up via your docker-compose.yml. Then build and run:

``` bash 
docker-compose up --build
```

Building Battle Service Separately

From the project root:

```bash
docker-compose build battle-service
docker-compose up battle-service
```
---

## 🌐 Accessing the Service

* Eureka Dashboard: http://localhost:8761
* Tournament API: http://localhost:8080/tournament?participants=8

---

## 📝 Notes

* The tournament currently supports participant counts as a power of 2 (e.g., 4, 8, 16).
* Adjust application.yml and config repo as needed for additional bot types or custom stats.
* To expand, consider saving match history, adding more abilities, or implementing advanced AI behavior.

--- 

Enjoy your automated fantasy bot battles! ⚔️

