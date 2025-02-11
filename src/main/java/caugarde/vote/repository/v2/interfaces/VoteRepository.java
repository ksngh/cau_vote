package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Vote;

import java.util.Optional;

public interface VoteRepository {

    void save(Vote vote);

    Optional<Vote> findById(Long id);

    Long countVoteByBoard(Board board);
}