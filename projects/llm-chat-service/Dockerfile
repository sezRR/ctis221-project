# Use a lightweight Java image
FROM openjdk:21-jdk

# Set working directory
WORKDIR /app

# Copy JAR and entrypoint script
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Use the custom entrypoint
ENTRYPOINT ["java","-jar","app.jar"]

#FROM openjdk:21-jdk
#WORKDIR /app
#COPY build/libs/*.jar app.jar
#COPY wait-for-it.sh wait-for-it.sh
#RUN chmod +x wait-for-it.sh
#EXPOSE 8080
#ENTRYPOINT ["./scripts/wait-for-it.sh", "postgres:5432", "--", "java", "-jar", "app.jar"]

