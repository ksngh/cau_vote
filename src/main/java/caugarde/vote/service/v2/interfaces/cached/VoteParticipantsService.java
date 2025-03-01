package caugarde.vote.service.v2.interfaces.cached;

import caugarde.vote.model.entity.Board;

public interface VoteParticipantsService {

    void delete(Long boardId);

    void create(Long boardId);

    void vote(Board board);

    void cancel(Long boardId);
}
