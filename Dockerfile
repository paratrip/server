# Use an official Gradle image to create the build artifact
FROM gradle:7.5.1-jdk17 as builder

# Set the working directory in the container
WORKDIR /app

# Copy build.gradle and settings.gradle
COPY build.gradle settings.gradle /app/

# Copy the source code
COPY src /app/src

# Build the application
RUN gradle build --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the executable jar file from the builder stage
COPY --from=builder /app/build/libs/paratrip-0.0.1-SNAPSHOT.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
