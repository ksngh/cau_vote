package caugarde.vote.service.redis;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.redis.CachedRanking;
import caugarde.vote.repository.redis.RankingRedisRepository;
import caugarde.vote.service.RankingService;
import caugarde.vote.service.SemesterService;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingRedisService {

    private final RedisTemplate<String, CachedRanking> redisTemplate;  // 개별 Ranking 객체
    private final RankingRedisRepository rankingRedisRepository;
    private final RankingService rankingService;
    private final SemesterService semesterService;

    private static final String RANKING_CACHE_KEY_PREFIX = "Ranking";
    private final StudentService studentService;

    public List<CachedRanking> getAllBySemester(Semester semester) {
        String currentCacheKey = getCurrentSemesterKey();
        List<CachedRanking> allRankings = redisTemplate.opsForList().range(currentCacheKey, 0, -1);
        return allRankings.stream()
                .filter(ranking -> ranking.getSemester().equals(semester))
                .collect(Collectors.toList());
    }

//    public List<CachedRanking> generateRankings(){
//        String str_semester = SemesterUtil.getSemester(LocalDate.now());
//        Semester semester = semesterService.getBySemester(str_semester);
//
////        CachedRanking cachedRanking = new CachedRanking(UUID.randomUUID(),)
//        return getAllBySemester(semester);
//    }

//    public void saveAll(CachedRanking ranking) {
//        LocalDate now = LocalDate.now();
//        Semester semester = semesterService.save(new Semester(UUID.randomUUID(),SemesterUtil.getSemester(now)));
//
//        if (!getAllBySemester(semester).isEmpty()) {
//            List<CachedRanking> allRankings = getAllBySemester(semester);
//            rankingRedisRepository.saveAll(allRankings);
//        }
//    }

    private String getCurrentSemesterKey() {
        // 학기를 구하는 로직, 예를 들어 2023년 1학기일 경우 "ranking:2023_1"
        String semester = SemesterUtil.getSemester(LocalDate.now());
        return RANKING_CACHE_KEY_PREFIX + semester;
    }

//    @Scheduled(cron = "0 0 0 * * MON")
//    public void generateWeeklyRanking() {
//        studentService.findAll()
//
//    }

//    public List<String> getAllCachedRankingTitles() {
//        return getAllCachedRankings().stream()
//                .map(CachedRanking::getTitle) // 타이틀 필드를 가져와
//                .collect(Collectors.toList()); //
//                리스트로 변환
//    }
}
