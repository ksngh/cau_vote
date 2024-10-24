# 1. 베이스 이미지 설정 (OpenJDK 21)
FROM openjdk:21-jdk-bullseye

# 2. 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y git fonts-nanum && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 3. 작업 디렉토리 설정
WORKDIR /app

# 4. 소스 코드 및 빌드 파일 복사
# GitHub Actions에서 가져온 소스 코드를 컨테이너로 복사
COPY . /app
COPY ./src/main/resources/keystore.p12 /app/src/main/resources/keystore.p12
COPY ./src/main/resources/application.properties /app/src/main/resources/application.properties

# 5. Gradle 및 wait-for-it.sh 파일에 실행 권한 부여
RUN chmod +x ./gradlew ./wait-for-it.sh

# 6. Gradle 빌드 (테스트 생략)
RUN ./gradlew build -x test

# 7. 애플리케이션 실행 (wait-for-it.sh 사용)
ENTRYPOINT ["./wait-for-it.sh", "redis:6379", "java", "-jar", "./build/libs/vote-0.0.1-SNAPSHOT.jar"]
