package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.QBoard;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.repository.v2.interfaces.jpa.BoardJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private static final QBoard qBoard = QBoard.board;
    private final JPAQueryFactory queryFactory;
    private final BoardJpaRepository boardJpaRepository;

    @Override
    public void save(Board board) {
        boardJpaRepository.save(board);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public List<BoardInfo.Response> searchBoard(Set<BoardStatus> statusSet) {
        return queryFactory
                .select(Projections.constructor(
                        BoardInfo.Response.class,
                        qBoard.id,
                        qBoard.title,
                        qBoard.content,
                        qBoard.status.stringValue(),
                        qBoard.createdAt
                ))
                .from(qBoard)
                .where(applyStatusFilter(statusSet),isNotDeleted())
                .orderBy(qBoard.createdAt.desc())
                .fetch();
    }

    @Override
    public long closeExpiredBoards(LocalDateTime now) {
        QBoard board = QBoard.board;

        return queryFactory
                .update(board)
                .set(board.status, BoardStatus.INACTIVE)
                .where(
                        board.status.eq(BoardStatus.ACTIVE),
                        board.endDate.before(now)
                )
                .execute();
    }


    private BooleanExpression applyStatusFilter(Set<BoardStatus> statuses) {
        if (statuses == null || statuses.isEmpty()) {
            return null;
        }
        return qBoard.status.in(statuses);
    }

    private BooleanExpression isNotDeleted() {
        return qBoard.deletedAt.isNull();
    }


}
