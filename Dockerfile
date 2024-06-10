# Use an official Maven image to build the app
FROM maven:3.9.7-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/webhooks-0.0.1-SNAPSHOT.jar ./app.jar

# Copy the script file
COPY script.sh /app/script.sh

# Make the script executable
RUN chmod +x /app/script.sh

# Expose the port the app runs on
EXPOSE 8080

# Run the web service on container startup
ENTRYPOINT ["java","-jar","app.jar"]
