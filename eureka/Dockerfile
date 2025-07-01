# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-24-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Build minimal runtime image
FROM eclipse-temurin:24-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]