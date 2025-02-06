package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.FencingType;

public interface VoteService {

    void create(Long boardId, FencingType fencingType, String email);

    Vote getById(Long id);

    void delete(Long boardId);

    int getVoteCount(Long boardId);
}
