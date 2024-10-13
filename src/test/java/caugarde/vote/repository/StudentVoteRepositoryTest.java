package caugarde.vote.repository;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.jpa.AuthorityRepository;
import caugarde.vote.repository.jpa.StudentRepository;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class StudentVoteRepositoryTest {

    @Autowired
    StudentVoteRepository studentVoteRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @BeforeEach
    void setUp() {
        UUID votePk = UUID.fromString("bba06759-1402-4b8d-9585-a1d1991f802c");
        UUID studentPk = UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c");


        Student student = new Student(studentPk,"20241014","test@test.com","수학과","김성호",authorityRepository.findByRole(Role.USER).orElse(null),"기존 회원");
        studentRepository.save(student);

        Vote vote = new Vote(votePk,"title","content",new Timestamp(System.currentTimeMillis()-3600),new Timestamp(System.currentTimeMillis()+3600),3);
        voteRepository.save(vote);

        studentVoteRepository.save(
                StudentVote.builder()
                        .studentVotePk(UUID.randomUUID())
                        .student(student)
                        .vote(vote)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        studentVoteRepository.deleteAll();
        studentRepository.deleteAll();
        voteRepository.deleteAll();
    }

    @Test
    @DisplayName("투표 갯수 가져오기")
    void findVoteCountByStudent() {
        // given: setUp() 메서드로 인해 데이터가 저장됨

        // when: 각 Student의 StudentVote 개수를 조회
        List<Object[]> attendanceCounts = studentRepository.findVoteCountByStudent();

        // then: 결과를 검증
        assertEquals(1, attendanceCounts.size(), "학생의 투표 수 개수는 1이어야 합니다.");

        // 첫 번째 요소의 studentPk가 맞는지 확인
        assertEquals(UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c"), attendanceCounts.get(0)[0]);
        // 첫 번째 요소의 attendanceCount가 1인지 확인
        assertEquals(1L, attendanceCounts.get(0)[1]);
        System.out.println(attendanceCounts.get(0)[0]);
        System.out.println(attendanceCounts.get(0)[1]);
    }

    @Test
    void save(){
        Vote vote = voteRepository.findById(UUID.fromString("9a21e8fa-b5f9-4be1-a990-0ea9558b7651")).orElse(null);
    }
}