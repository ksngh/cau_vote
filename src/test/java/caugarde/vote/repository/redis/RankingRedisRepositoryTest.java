package caugarde.vote.repository.redis;

import caugarde.vote.common.util.SemesterUtil;
import caugarde.vote.model.entity.Ranking;
import caugarde.vote.model.entity.Semester;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RankingRedisRepositoryTest {

    @Mock
    RankingRedisRepository rankingRedisRepository;

    @Test
    void deleteBySemester() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void findBySemester() {
    }

    @Test
    void save() {
    }

    @Test
    void findRankingsBySemester() {
        //given
        String str_semester = SemesterUtil.getSemester(LocalDate.now());
        Semester semester = new Semester(UUID.randomUUID(), str_semester);

        //when
        List<Ranking> rankingList = rankingRedisRepository.findRankingsBySemester(semester);

        //then
        System.out.println(rankingList);
    }
}