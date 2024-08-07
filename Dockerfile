# Use an official Gradle image to create the build artifact
FROM gradle:7.5.1-jdk17 as builder

# Set the working directory in the container
WORKDIR /app

# Copy build.gradle and settings.gradle
COPY build.gradle settings.gradle /app/

# Copy the source code
COPY src /app/src

# Copy application.yml and application-production.yml
COPY src/main/resources/application.yml /app/src/main/resources/application.yml
COPY src/main/resources/application-production.yml /app/src/main/resources/application-production.yml

# Build the application
RUN gradle build --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the builder stage
COPY --from=builder /app/build/libs/paratrip-0.0.1-SNAPSHOT.jar app.jar

# Copy application.yml and application-production.yml
COPY src/main/resources/application.yml /app/application.yml
COPY src/main/resources/application-production.yml /app/application-production.yml

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set environment variable to activate production profile
ENV SPRING_PROFILES_ACTIVE=production

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
