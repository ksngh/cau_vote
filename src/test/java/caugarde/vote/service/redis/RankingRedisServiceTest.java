package caugarde.vote.service.redis;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.entity.redis.CachedRanking;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.jpa.AuthorityRepository;
import caugarde.vote.repository.jpa.StudentRepository;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import caugarde.vote.service.SemesterService;
import caugarde.vote.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class
RankingRedisServiceTest {

    @Autowired
    private RedisTemplate<String, CachedRanking> redisTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private RankingRedisService rankingRedisService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    StudentVoteRepository studentVoteRepository;

    private Semester semester;
    private List<Object[]> attendanceList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID votePk = UUID.fromString("bba06759-1402-4b8d-9585-a1d1991f802c");
        UUID studentPk = UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c");


        Student student = new Student(studentPk,"20241014","test@test.com","수학과","김성호",authorityRepository.findByRole(Role.USER).orElse(null),"기존 회원");
        studentRepository.save(student);

        Vote vote = new Vote(votePk,"title","content",new Timestamp(System.currentTimeMillis()-3600),new Timestamp(System.currentTimeMillis()+3600),3);
        voteRepository.save(vote);

        UUID svkPk =UUID.fromString("aca06759-1402-4b8d-9585-a1d1991f802c");
        studentVoteRepository.save(
                StudentVote.builder()
                        .studentVotePk(svkPk)
                        .student(student)
                        .vote(vote)
                        .build()
        );

        // 테스트용 학기 객체 생성
        semester = semesterService.save(new Semester(UUID.randomUUID(), SemesterUtil.getSemester(LocalDate.now()))) ;

        attendanceList = studentService.findVoteCountByStudent();

    }

    @AfterEach
    void tearDown() {
//        studentVoteRepository.deleteById(UUID.fromString("aca06759-1402-4b8d-9585-a1d1991f802c"));
//        studentRepository.deleteById(UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c"));
//        voteRepository.deleteById(UUID.fromString("bba06759-1402-4b8d-9585-a1d1991f802c"));
        rankingRedisService.deleteAllByKey(rankingRedisService.getCurrentKey());
    }

    @Test
    void testGenerateCache() {
        // generateCache 메서드 실행
        List<CachedRanking> cachedRankings = rankingRedisService.generateRankings();

        // 예상되는 결과 검증
        assertEquals(studentRepository.count(), cachedRankings.size(), "생성된 캐싱 랭킹 수가 예상과 일치하지 않습니다.");
        CachedRanking cachedRanking = cachedRankings.get(0);
        assertEquals(semester, cachedRanking.getSemester(), "학기가 예상과 일치하지 않습니다.");
    }


    @Test
    void getCachedRankings() {
        //given
        List<CachedRanking> cachedRankings = rankingRedisService.generateRankings();

        //when
        List<CachedRanking> cachedRankingList = rankingRedisService.getCachedRankings();

        //then
        System.out.println(cachedRankingList.getFirst().getAttendanceCount());
        System.out.println(cachedRankingList.getFirst().getStudent().getName());
    }

    @Test
    void generateRankings() {
    }

    @Test
    void generateCache() {
    }

    @Test
    void getCurrentKey() {
    }

    @Test
    void deleteAllByKey() {
    }

    @Test
    void generateWeeklyRanking() {
    }

    @Test
    void generateEndOfMonthRanking() {
    }
}