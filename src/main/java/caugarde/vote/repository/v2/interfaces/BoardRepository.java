package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.entity.Board;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    void save(Board board);

    Optional<Board> findById(Long id);

    Slice<BoardInfo.Response> getPages(Long cursorId, int size);

    Slice<BoardInfo.Response> getUserPages(String email, Long cursorId, int size);

    List<Long> closeExpiredBoards();

    List<Long> activateBoards();
}
