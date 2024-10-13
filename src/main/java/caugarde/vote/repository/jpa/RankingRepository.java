package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, UUID> {

//    void saveAll(List<Ranking> rankings);
}
