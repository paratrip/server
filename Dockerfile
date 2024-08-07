# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리를 설정
WORKDIR /app

# 파일 복사
COPY build/libs/paratrip-0.0.1-SNAPSHOT.jar app.jar

# 커맨드 실행
CMD ["java", "-jar", "app.jar"]
