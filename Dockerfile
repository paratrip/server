# 빌드 단계
FROM bellsoft/liberica-openjdk-alpine:17 as build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# 실행 단계
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/src/main/resources/application*.yml ./src/main/resources/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]