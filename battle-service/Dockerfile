# Use a recent Eclipse Temurin JDK image
FROM eclipse-temurin:24-jdk

# Create a working directory
WORKDIR /app

# Define the JAR file location (built externally)
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the application port
EXPOSE 8080

# Define the entry point
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
