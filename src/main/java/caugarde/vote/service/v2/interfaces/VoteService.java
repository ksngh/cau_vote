package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.vote.VoteCreate;
import caugarde.vote.model.dto.vote.VoteInfo;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.FencingType;

import java.util.List;

public interface VoteService {

    void create(VoteCreate.Request request, String email);

    List<VoteInfo.Response> getByBoardId(Long boardId);

    void delete(Vote vote);

    Integer getVoteCount(Long boardId);

    Vote getByBoardAndStudent(Long boardId, String email);
}
