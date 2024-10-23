# 1. 베이스 이미지 설정 (OpenJDK 21)
FROM openjdk:21-jdk-bullseye

# 2. 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y git fonts-nanum && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 3. 작업 디렉토리 설정
WORKDIR /app

# 4. Git 레포지토리 복사
RUN git clone --depth=1 -b main https://github.com/ksngh/cau_vote cau_vote

# 5. Gradle 실행 권한 설정 및 wait-for-it.sh 복사
WORKDIR /app/cau_vote
COPY ./wait-for-it.sh /app/cau_vote/wait-for-it.sh
RUN chmod +x gradlew wait-for-it.sh

# 6. 애플리케이션의 리소스 파일 복사
COPY ./src/main/resources/application.properties ./src/main/resources/
COPY ./src/main/resources/keystore.p12 ./src/main/resources/

# 7. Gradle을 사용해 빌드 (테스트 생략)
RUN ./gradlew build -x test

# 8. 애플리케이션 실행 (wait-for-it.sh 사용)
ENTRYPOINT ["./wait-for-it.sh", "redis:6379", "--", "java", "-jar", "./build/libs/vote-0.0.1-SNAPSHOT.jar"]
