package caugarde.vote.repository.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;

import java.util.Optional;

public interface VoteParticipantsRepository {

    Optional<VoteParticipants> findByBoardId(Long boardId);

    void save(Long boardId,VoteParticipants voteParticipants);

    void delete(Long boardId);

    void decrementVoteCount(Long boardId);

    Long incrementVoteCount(Long boardId);
}
