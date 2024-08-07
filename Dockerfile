# 빌드 단계
FROM openjdk:17-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# 실행 단계
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# 환경 설정 파일 복사
COPY --from=build /app/src/main/resources/application*.yml ./src/main/resources/
COPY --from=build /app/.env ./

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
