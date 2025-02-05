package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.board.BoardCreate;

public interface BoardService {

    void create(BoardCreate.Request request);
}
