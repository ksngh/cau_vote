package caugarde.vote.repository.redis;

import caugarde.vote.model.entity.Ranking;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.redis.CachedRanking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RankingRedisRepository extends CrudRepository<CachedRanking, UUID> {

    void deleteBySemester(Semester semester);

    void saveAll(Semester semester, List<Ranking> rankings);

    List<CachedRanking> findBySemester(Semester semester);

    void save(Ranking ranking);

    List<CachedRanking> findAllByAttendanceCount(int attendanceCount);
}
