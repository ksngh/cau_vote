-- AUTHORITY 테이블에 더미 데이터 삽입
INSERT INTO authority (authority_pk, role) VALUES
                                               (UUID(), 'ADMIN'),
                                               (UUID(), 'USER');

-- ADMIN 테이블에 더미 데이터 삽입
INSERT INTO admin (admin_pk, id, password, authority_fk) VALUES
                                                             (UUID(), 'admin1', '1234', (SELECT authority_pk FROM authority WHERE role = 'ADMIN')));

-- STUDENT 테이블에 더미 데이터 삽입
INSERT INTO student (student_pk, email, majority, member_type, name, student_id, authority_fk) VALUES
                                                                                                   (UUID(), 'jin123@naver.com', '컴퓨터공학과', '기존', '김우진', '20164123', (SELECT authority_pk FROM authority WHERE role = 'USER')),
                                                                                                   (UUID(), 'minjun0611@naver.com', '미디어커뮤니케이션학부', '신입', '서민준', '20201182', (SELECT authority_pk FROM authority WHERE role = 'USER'));

-- VOTE 테이블에 더미 데이터 삽입
INSERT INTO vote (vote_pk, title, limit_people, start_date, submit_date, content) VALUES
                                                                                      (UUID(), '24학년도 2학기 24/09/15 훈련', 20, NOW(), NOW() + INTERVAL 7 DAY, '훈련공지

🤺중앙가르드 9월 정기훈련 안내🤺

9월 정기훈련은
- 월요일 : 20~22시
- 수요일 : 20~22시에 진행됩니다.

📍장소 : 305관(체육관) 116호 스포츠창작실 (동아리방에서 집합 후 필요한 장비들을 챙기고 다같이 체육관으로 이동할 예정입니다.)
📍훈련 준비물 : 실내용 운동화 (실외에서 착용하지 않는 신발만 가능하며, 실외용 신발은 불가능합니다.)

🚩매 훈련 시 최소인원 10명을 채우지 못할 경우에 체육관 대관이 불가능합니다.

따라서 운동 투표를 하시고 뒤늦게 불참을 하거나, 투표를 하지 않고 참여하는 일은 없도록 개인 일정 확인 후 꼭 투표 및 댓글 부탁드립니다.

🚩투표 시 댓글에 이름/학과/학번 작성해주세요.

🔥개인 사정으로 급하게 늦참, 불참시에 홍유나에게 개인톡 부탁드립니다. ,'),
                                                                                      (UUID(), '24학년도 2학기 24/10/21 훈련', 20, NOW(), NOW() + INTERVAL 7 DAY, '훈련공지

🤺중앙가르드 9월 정기훈련 안내🤺

9월 정기훈련은
- 월요일 : 20~22시
- 수요일 : 20~22시에 진행됩니다.

📍장소 : 305관(체육관) 116호 스포츠창작실 (동아리방에서 집합 후 필요한 장비들을 챙기고 다같이 체육관으로 이동할 예정입니다.)
📍훈련 준비물 : 실내용 운동화 (실외에서 착용하지 않는 신발만 가능하며, 실외용 신발은 불가능합니다.)

🚩매 훈련 시 최소인원 10명을 채우지 못할 경우에 체육관 대관이 불가능합니다.

따라서 운동 투표를 하시고 뒤늦게 불참을 하거나, 투표를 하지 않고 참여하는 일은 없도록 개인 일정 확인 후 꼭 투표 및 댓글 부탁드립니다.

🚩투표 시 댓글에 이름/학과/학번 작성해주세요.

🔥개인 사정으로 급하게 늦참, 불참시에 홍유나에게 개인톡 부탁드립니다.');

-- STUDENT_VOTE 테이블에 더미 데이터 삽입
INSERT INTO student_vote (student_vote_pk, student_fk, vote_fk) VALUES
                                                                            (UUID(), 1, (SELECT student_pk FROM student LIMIT 1), (SELECT vote_pk FROM vote LIMIT 1)),
(UUID(), (SELECT student_pk FROM student LIMIT 1 OFFSET 1), (SELECT vote_pk FROM vote LIMIT 1 OFFSET 1));