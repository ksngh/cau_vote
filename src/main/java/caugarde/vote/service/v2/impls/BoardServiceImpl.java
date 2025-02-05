package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.common.util.SecurityUtil;
import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.service.v2.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public void create(BoardCreate.Request request) {
        Board board = Board.from(request);
        boardRepository.save(board);
    }

    @Override
    public void update(BoardUpdate.Request request,Integer id) {
        Board board = getById(id);

        board.update(request,SecurityUtil.getCurrentUserEmail());
        boardRepository.save(board);
    }

    @Override
    public void delete(Integer id) {
        Board board = getById(id);
        board.onSoftDelete(SecurityUtil.getCurrentUserEmail());
    }

    @Override
    public Board getById(Integer id) {
        return boardRepository.findById(id).orElseThrow(()-> new CustomApiException(ResErrorCode.NOT_FOUND,"해당하는 투표 게시글을 찾을 수 없습니다."));
    }

    @Override
    public List<BoardInfo.Response> search(Set<BoardStatus> boardStatusSet) {
        return boardRepository.searchBoard(boardStatusSet);
    }



}
