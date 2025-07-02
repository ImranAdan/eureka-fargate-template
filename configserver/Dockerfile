# Stage 1: Build with Maven + Java 21 (last supported official image)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# Stage 2: Run on Java 24 (your target)
FROM eclipse-temurin:24-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
