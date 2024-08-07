# 빌드 단계
FROM openjdk:17-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# 실행 단계
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["/app/wait-for-it.sh", "es", "9200", "--", "/app/wait-for-it.sh", "kibana", "5601", "--", "java", "-jar", "app.jar"]
