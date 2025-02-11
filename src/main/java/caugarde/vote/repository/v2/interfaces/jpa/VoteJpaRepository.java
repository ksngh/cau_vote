package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteJpaRepository extends JpaRepository<Vote, Long> {

    long countVoteByBoard(Board board);
}
