# 🛠️ Config Server

This is the Spring Cloud Config Server for the Eureka Fargate Template project.

## 📦 What It Does

- Acts as a centralized configuration service for microservices in your ecosystem.
- Loads configurations from a Git-based repository.
- Registers itself with the Eureka service registry so other services can discover it.
- Supports Spring Cloud Config profiles and labels for flexible environment-specific configurations.

## 🚀 How It Works

1. Fetches configuration files (e.g., `application.yml`, `service-name.yml`) from your configured Git repository.
2. Exposes configuration endpoints under:

`http://<config-server-host>:8888/{application}/{profile}/{label}` 

3. Registers with Eureka to allow microservices to locate it dynamically.

## 🔧 Key Environment Variables

- `SPRING_CLOUD_CONFIG_SERVER_GIT_URI` — the Git repository URL where configurations live.
- `SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME` — Git username for private repos.
- `SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD` — Git access token for authentication.

These are typically passed via environment variables during container start or in your CI/CD pipelines.
