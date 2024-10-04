# 베이스 이미지
FROM openjdk:21-jdk-bullseye

# OpenJDK 설치 (OpenJDK 21설치)
RUN apt-get update && \
    apt-get install -y git fonts-nanum && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# Clone the Git repository. Here we dynamically specify the repository name using the variable defined earlier.
# Git repository를 최신 상태로 가져오도록 변경
RUN git clone --depth=1 -b main https://github.com/ksngh/cau_vote cau_vote

# Changes the working directory to /app/${REPO_NAME}. This uses the variable to dynamically set the directory path.
WORKDIR /app/cau_vote
RUN rm -rf .git

COPY ./src/main/resources/application.properties ./src/main/resources/
COPY ./src/main/resources/keystore.p12 ./src/main/resources/

# JAR 파일 빌드 (테스트 생략)
WORKDIR /app/cau_vote
RUN chmod +x gradlew
RUN ./gradlew build -x test


# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "./build/libs/cau_vote-0.0.1-SNAPSHOT.jar"]