package caugarde.vote.service.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;

public interface VoteParticipantsService {

    VoteParticipants getByBoardId(Long boardId);

    void save(Long boardId, VoteParticipants voteParticipants);

    void delete(Long boardId);

    boolean update(Long boardId, VoteParticipants oldVoteParticipants,VoteParticipants newVoteParticipants);
}
