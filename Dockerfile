# === 빌드 스테이지 ===
FROM openjdk:21-jdk-bullseye AS build
ENV TZ=Asia/Seoul
WORKDIR /app

# Gradle 관련 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 의존성 미리 다운로드 (캐싱 활용)
RUN chmod +x ./gradlew && ./gradlew dependencies

# 애플리케이션 소스코드 복사
COPY src src

# 빌드 수행 (테스트 제외)
RUN ./gradlew clean build -x test

# === 런타임 스테이지 ===
FROM openjdk:21-jdk-bullseye
ENV TZ=Asia/Seoul
WORKDIR /app

# wait-for-it.sh 추가 및 권한 부여
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh

# 이전 빌드 단계에서 jar파일만 복사
COPY --from=build /app/build/libs/vote-0.0.1-SNAPSHOT.jar app.jar

# 컨테이너 실행 명령어 (Redis, MariaDB 둘 다 기다리는 방식 권장)
ENTRYPOINT ["./wait-for-it.sh", "vote_db:3306", "-t", "60", "--", \
            "./wait-for-it.sh", "vote_redis:6379", "-t", "60", "--", \
            "java", "-jar", "/app/app.jar"]