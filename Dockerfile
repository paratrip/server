# 빌드 단계
FROM bellsoft/liberica-openjdk-alpine:17 as build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build

# 실행 단계
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
