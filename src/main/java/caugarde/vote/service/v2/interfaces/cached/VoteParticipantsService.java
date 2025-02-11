package caugarde.vote.service.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;

public interface VoteParticipantsService {

    void delete(Long boardId);

    void create(Long boardId,VoteParticipants voteParticipants);

    void vote(Long boardId,int limitPeople);

    void cancel(Long boardId);
}
