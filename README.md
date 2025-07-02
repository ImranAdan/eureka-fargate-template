# 🌐 Eureka Fargate Template

A template to bootstrap microservices on AWS Fargate using Spring Cloud Eureka for service discovery and Spring Cloud Config for externalized configuration. Designed for developers to plug in their services with minimal effort and focus on building business logic.

---

## 📦 What’s Included

✅ **Eureka Server** – service registry for your microservices  
✅ **Config Server** – externalized configuration server pulling from GitHub  
✅ **Battle Service** – sample microservice demonstrating Eureka and Config integration  
✅ **Dockerized builds** – lean images with multi-stage Dockerfiles  
✅ **CI/CD** – GitHub Actions pipelines using OIDC to securely push images to ECR  
✅ **Terraform** – modules to provision ECR repositories for your services

---

## 🚀 Clone the Repository

```bash
git clone https://github.com/your-org/eureka-fargate-template.git
cd eureka-fargate-template
```

---

## 🔧 Configuration

### 🔹 Externalized Config

* Your Config Server must pull config from a Git repo (e.g., your private or public GitHub config repo).
* Update the `application.yml` in `configserver` or pass at runtime:

```bash 
export SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/your-org/config-repo
export SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME=your-username
export SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=your-github-token
```

### 🔹 AWS Credentials

For local builds: configure AWS CLI with a profile.

For GitHub Actions CI: add these repository secrets:

* AWS_ACCOUNT_ID
* AWS_REGION

For OIDC: deploy the Terraform IAM role for GitHub Actions OIDC and configure your workflows to use it.

---

# 🛠 Build Locally

Each service (eureka, configserver, battle-service) uses a multi-stage Dockerfile. Example for Eureka:

```bash
cd eureka
docker build -t eureka-server:local .
```

Run locally:

```bash
docker run --rm -p 8761:8761 eureka-server:local
```

Access Eureka dashboard: http://localhost:8761

---


## 🏗 Build and Push to ECR (CI/CD)

Each service has its own GitHub Actions workflow:

* `.github/workflows/eureka-ci.yml`
* `.github/workflows/configserver-ci.yml`
* `.github/workflows/battle-ci.yml`

These:

* Build and verify Maven artifacts.
* Build and tag Docker images.
* Push images securely to ECR using GitHub OIDC (no static AWS keys).

---

## ☁️ Deploy to AWS Fargate

* 1️⃣ Provision ECR repos

Terraform modules in terraform/modules/ecr handle creation of ECR repositories for your services.

* 2️⃣ Build ECS Infrastructure

    ECS cluster

    Fargate task definitions

    ALB (if needed)

    Service discovery (AWS Cloud Map) or internal ALB targets

* 3️⃣ Update Task Definitions

    Reference ECR image URIs built by your CI pipelines.

    Set environment variables pointing to Eureka and Config Server endpoints.

* 4️⃣ Terraform Apply

```bash 
terraform init
terraform plan
terraform apply
```

---

## ✅ Test

* After deployment, Eureka should show all registered services on your public/private URL.
* Config Server should serve app configurations from your Git repo.
* Sample service (Battle Service) should register with Eureka and fetch configuration on startup.

Example: Check config for battle-service from Config Server:

```bash 
curl http://<config-server-endpoint>:8888/battle-service/default
```

