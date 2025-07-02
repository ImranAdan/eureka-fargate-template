# Stage 1: Build with Maven + Java 21 (last supported official image)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Declare build-time arguments for config repo credentials
ARG CONFIG_GIT_URI
ARG CONFIG_GIT_USERNAME
ARG CONFIG_GIT_PASSWORD

# Use them during build
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_URI=$CONFIG_GIT_URI
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME=$CONFIG_GIT_USERNAME
ENV SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=$CONFIG_GIT_PASSWORD

COPY . .
RUN mvn clean package

# Stage 2: Run on Java 24 (your target)
FROM eclipse-temurin:24-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
