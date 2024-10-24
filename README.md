# CAU_VOTE




## 📖 프로젝트 소개

중앙대학교 펜싱 동아리 훈련 투표 시스템입니다.
정해진 시각에 투표가 열리며 제한 인원이 차게 되면 마감됩니다.

### 개발 인원
* 김성호   [@ksngh](https://github.com/ksngh)

### 개발 기간
* 2024.09.09 ~ (진행 중)

  
<br>


## 🖋 기술 스택
* front
  * JavaScript
* Back
  * Java 21
  * Spring boot 3.3.2
  * Spring Data Jpa
  * MariaDB 11.4.2
* AWS
  * EC2
* Docker
* Redis



<hr />

## 📌 구현 기능

<details><summary>게시판 관리
</summary>

 * [API(코드 리뷰 게시판)](https://github.com/HTA-2402-TEAM3/codeback/blob/master/src/main/java/kr/codeback/api/CodeReviewRestController.java)
 * [API(프로젝트 리뷰 게시판)](https://github.com/HTA-2402-TEAM3/codeback/blob/master/src/main/java/kr/codeback/api/ProjectReviewRestController.java)
 * 게시판 생성
   * 마크다운 에디터 사용
   * aws s3 이미지 업로드
 * 게시판 삭제
 * 게시판 수정
 * 게시판 조회
   * 게시판 검색 조건별 게시글 목록 조회
</details>

<summary>댓글 관리</summary>

* [API(코드 리뷰 댓글)](https://github.com/HTA-2402-TEAM3/codeback/blob/master/src/main/java/kr/codeback/api/CodeReviewCommentRestController.java)
* [API(프로젝트 리뷰 댓글)](https://github.com/HTA-2402-TEAM3/codeback/blob/master/src/main/java/kr/codeback/api/ProjectReviewCommentRestController.java)
 * 댓글 생성
 * 댓글 삭제
 * 댓글 수정

<details><summary>관리자 페이지</summary>
<br />

### 🎨구현 화면
<br />





