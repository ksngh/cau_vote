package caugarde.vote.repository.redis;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Ranking;
import caugarde.vote.model.entity.Semester;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.redis.CachedRanking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankingRedisRepositoryTest {

    @Autowired
    RankingRedisRepository rankingRedisRepository;

    @Test
    void deleteBySemester() {
    }

    @Test
    void findAll(){
        rankingRedisRepository.findAll();
    }

    @Test
    void saveAll() {
    }

    @Test
    void findBySemester() {
    }

    @Test
    void save() {

        CachedRanking ranking = new CachedRanking(UUID.randomUUID(),new Student(),new Semester(),3);
        rankingRedisRepository.save(ranking);
    }

    @Test
    void findAllByAttendanceCount(){
        System.out.println(rankingRedisRepository.findAllByAttendanceCount(0));

        Assertions.assertTrue(rankingRedisRepository.findAllByAttendanceCount(1).isEmpty());
    }

}