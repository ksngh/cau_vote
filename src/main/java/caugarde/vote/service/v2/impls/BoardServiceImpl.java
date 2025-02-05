package caugarde.vote.service.v2.impls;

import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.entity.Board;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.service.v2.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public void create(BoardCreate.Request request) {
        Board board = Board.from(request);
        boardRepository.save(board);
    }

}
