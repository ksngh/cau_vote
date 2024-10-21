package caugarde.vote.service.redis;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Ranking;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.redis.CachedRanking;
import caugarde.vote.service.RankingService;
import caugarde.vote.service.SemesterService;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingRedisService {

    private final RedisTemplate<String, CachedRanking> redisTemplate;  // 개별 Ranking 객체
    private final StudentService studentService;
    private final RankingService rankingService;
    private final SemesterService semesterService;

    private static final String RANKING_CACHE_KEY_PREFIX = "Ranking:";

    //해당 학기 것만 탐색
    public List<CachedRanking> getCachedRankings() {
        return redisTemplate.opsForList().range(getCurrentKey(), 0, -1);
    }

    //해당 학기에 랭킹을 생성
    public List<CachedRanking> generateRankings() {
        String str_semester = SemesterUtil.getSemester(LocalDate.now());
        Semester semester = semesterService.save(new Semester(UUID.randomUUID(), str_semester));
        List<Object[]> attendanceList = studentService.getAttendanceList();
        return generateCache(attendanceList, semester);
    }

    public List<Ranking> getWholeRankings() {
        List<CachedRanking> allRankings = new ArrayList<>();

        // 모든 키를 조회 (패턴에 맞는 키만 가져올 수도 있음, 예: "Ranking:*")
        Set<String> keys = redisTemplate.keys("Ranking:*"); // "Ranking:"으로 시작하는 키들만 가져오기

        if (keys != null) {
            for (String key : keys) {
                // 각 키에 대해 해당 리스트 데이터를 조회
                List<CachedRanking> rankings = redisTemplate.opsForList().range(key, 0, -1);
                if (rankings != null) {
                    allRankings.addAll(rankings); // 모든 리스트 데이터를 하나로 합침
                }
            }
        }

        return cachedToRanking(allRankings);
    }

    //해당 학기 랭킹을 캐쉬에 등록
    public List<CachedRanking> generateCache(List<Object[]> attendanceList, Semester semester) {
        List<CachedRanking> cachedRankings = new ArrayList<>();
        for (Object[] attendance : attendanceList) {
            UUID studentPk = (UUID) attendance[0];  // 학생의 Primary Key
            int attendanceCount = ((Long) attendance[1]).intValue();  // 출석 수

            // CachedRanking 객체 생성
            CachedRanking cachedRanking = new CachedRanking(
                    UUID.randomUUID(),    // 새로 생성한 UUID
                    studentService.findById(studentPk),            // 학생 PK
                    semester,             // 학기 객체
                    attendanceCount       // 출석 수
            );

            // 생성한 객체를 리스트에 추가
            cachedRankings.add(cachedRanking);
        }
        redisTemplate.opsForList().rightPushAll(getCurrentKey(), cachedRankings);
        return cachedRankings;
    }

    public String getCurrentKey() {
        return RANKING_CACHE_KEY_PREFIX + SemesterUtil.getSemester(LocalDate.now());
    }

    public void deleteAllByKey(String key) {
        redisTemplate.delete(key);  // 키 목록에 맞는 값 모두 삭제
    }


    @Scheduled(cron = "0 0 0 * * MON")
    public void generateWeeklyRanking() {
        deleteAllByKey(getCurrentKey());
        generateRankings();
    }

    @Scheduled(cron = "0 0 23 28-31 2,8 ?")
    public void generateEndOfMonthRanking() {
        LocalDate today = LocalDate.now();
        int lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()).getDayOfMonth();

        // 2월과 8월의 마지막 날에만 실행
        if ((today.getMonthValue() == 2 || today.getMonthValue() == 8) && today.getDayOfMonth() == lastDayOfMonth) {

            List<CachedRanking> cachedRankings = generateRankings();
            redisTemplate.opsForList().rightPushAll(getCurrentKey(), cachedRankings);

            List<Ranking> rankings = cachedRankings.stream().map(Ranking::new).toList();
            rankingService.saveRankings(rankings);
        }
    }

    public List<Ranking> getFirstGrade() {
        List<Ranking> rankings = getWholeRankings();
        int attendanceCount = getTopAttendanceCount(rankings);
        return rankings.stream()
                .filter(ranking -> ranking.getAttendanceCount() == attendanceCount)
                .toList();
    }

    public int getTopAttendanceCount(List<Ranking> rankings) {
        return rankings.stream()
                .mapToInt(Ranking::getAttendanceCount)
                .max()
                .orElse(0);
    }

    public List<Ranking> cachedToRanking(List<CachedRanking> cachedRankings) {
        return cachedRankings.stream()
                .map(Ranking::new)  // 람다식 대신 메서드 레퍼런스 사용
                .collect(Collectors.toList());
    }

}
