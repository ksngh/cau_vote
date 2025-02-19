package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.enums.BoardStatus;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BoardRepository {

    void save(Board board);

    Optional<Board> findById(Long id);

    Slice<BoardInfo.Response> getPages(Long cursorId, int size);

    List<Long> closeExpiredBoards();

    List<Long> activateBoards();
}
