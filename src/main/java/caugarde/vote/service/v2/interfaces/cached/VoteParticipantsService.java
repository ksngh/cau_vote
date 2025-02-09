package caugarde.vote.service.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;

public interface VoteParticipantsService {

    VoteParticipants getByBoardId(Long boardId);

    void delete(Long boardId);

    void create(Long boardId,VoteParticipants voteParticipants);

    void vote(Long boardId);

    void cancel(Long boardId);
}
