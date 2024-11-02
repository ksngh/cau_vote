# CAU VOTE

## 📖 프로젝트 소개

### (24.09.09~) **배포 중**
[**GitHub 주소**](https://github.com/ksngh/cau_vote) | [**배포 주소**](https://caufencing.com)

![투표 시스템](https://github.com/user-attachments/assets/6fc65b6b-7073-4a09-9c44-fb63cc8e18b9)

![투표 시스템 모달창](https://github.com/user-attachments/assets/f4d89b0e-7048-43cc-8a9a-fc030ee911aa)

> **중앙대학교 펜싱동아리 훈련 투표 사이트입니다.**  
> 1인 개발 프로젝트로, 현재 **50명 이상**이 사용 중입니다.  
> 정해진 시각에 투표가 열려 선착순으로 투표할 수 있습니다.

---

### 기술 스택
![Java](https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=Spring%20Security&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-007396?style=for-the-badge&logo=JPA&logoColor=white)
![MariaDB](https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/amazon%20EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white)
![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
![Git](https://img.shields.io/badge/GIT-F05032?style=for-the-badge&logo=git&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white)

---

### 📌 구현 기능

#### 1. 캐싱 처리 및 스케줄러 관리
- **캐싱 처리**: Redis를 이용하여 랭킹 조회 로직을 캐싱 처리.
- **스케줄링 관리**: Spring Scheduler를 활용하여 주간 및 학기별 업데이트를 자동으로 실행.

#### 2. 실시간 데이터 업데이트 및 동시성 문제 해결
- **실시간 업데이트**: 웹소켓 API와 STOMP를 이용해 실시간 투표 정보를 업데이트.
- **동시성 문제 해결**: ConcurrentLinkedQueue를 통해 순차적인 투표 진행을 보장.
- **안전한 투표 취소**: ConcurrentHashMap을 사용하여 안전하게 투표 취소 기능 구현.

#### 3. 테스트 코드 작성
- **단위 테스트**: JUnit과 Mockito를 사용하여 의존성 모킹 및 단위 테스트 구현.

#### 4. 예외 처리
- **전역 예외 처리**: GlobalExceptionHandler를 구현하여 전역 예외 처리 로직을 작성.

#### 5. CI/CD 파이프라인 구축
- **자동 배포**: Docker와 GitHub Actions를 활용하여 컨테이너 환경에서 배포 및 CI/CD 파이프라인 구축.






