# Use a lightweight Java image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy JAR and entrypoint script
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Use the custom entrypoint
ENTRYPOINT ["java","-jar","app.jar"]
