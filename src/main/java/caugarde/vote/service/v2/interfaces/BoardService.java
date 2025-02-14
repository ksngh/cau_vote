package caugarde.vote.service.v2.interfaces;

import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.enums.BoardStatus;

import java.util.List;
import java.util.Set;

public interface BoardService {

    void create(BoardCreate.Request request, CustomOAuthUser user);

    void update(BoardUpdate.Request request, Long id,String email);

    void delete(Long id,String email);

    Board getById(Long id);

    List<BoardInfo.Response> search(Set<BoardStatus> boardStatusSet);

    void closeExpiredBoards();

}
