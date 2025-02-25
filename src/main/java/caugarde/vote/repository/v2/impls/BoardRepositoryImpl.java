package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.QBoard;
import caugarde.vote.model.entity.QStudent;
import caugarde.vote.model.entity.QVote;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.repository.v2.interfaces.jpa.BoardJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private static final QBoard qBoard = QBoard.board;
    private static final QVote qVote = QVote.vote;
    private static final QStudent qStudent = QStudent.student;

    private final JPAQueryFactory queryFactory;
    private final BoardJpaRepository boardJpaRepository;

    @Override
    public void save(Board board) {
        boardJpaRepository.save(board);
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findByDeletedAtIsNullAndId(id);
    }

    @Override
    public Slice<BoardInfo.Response> getPages(Long cursorId, int size) {
        List<BoardInfo.Response> items = queryFactory
                .select(Projections.constructor(
                        BoardInfo.Response.class,
                        qBoard.id,
                        qBoard.title,
                        qBoard.content,
                        qBoard.status,
                        qBoard.limitPeople,
                        qBoard.startDate,
                        qBoard.endDate
                ))
                .from(qBoard)
                .where(cursorId != null ? qBoard.id.lt(cursorId) : null, isNotDeleted()) // cursorId보다 작은 ID만 가져오기
                .orderBy(qBoard.createdAt.desc())  // 최신순 정렬
                .limit(size + 1)  // 다음 페이지 여부 확인을 위해 요청 개수보다 +1
                .fetch();

        boolean hasNext = items.size() > size; // 다음 페이지가 있는지 확인

        if (hasNext) {
            items.removeLast(); // 실제 응답에서는 +1한 데이터 제외
        }

        return new SliceImpl<>(items, PageRequest.of(0, size), hasNext);
    }

    @Override
    public Slice<BoardInfo.Response> getUserPages(String email, Long cursorId, int size) {
        List<BoardInfo.Response> items;

        items = queryFactory
                .select(Projections.constructor(
                        BoardInfo.Response.class,
                        qBoard.id,
                        qBoard.title,
                        qBoard.content,
                        qBoard.status,
                        qBoard.limitPeople,
                        qBoard.startDate,
                        qBoard.endDate
                ))
                .from(qVote) // `vote` 테이블을 기준으로 시작
                .join(qStudent).on(qVote.student.id.eq(qStudent.id)) // `vote` → `student` JOIN
                .join(qBoard).on(qVote.board.id.eq(qBoard.id)) // `vote` → `board` JOIN
                .where(qStudent.email.eq(email))
                .where(isVoteNotDeleted())// `student.email` 필터링
                .where(cursorId != null ? qBoard.id.lt(cursorId) : null) // 커서 페이징 적용
                .where(isNotDeleted()) // 삭제되지 않은 데이터만 가져오기
                .orderBy(qBoard.createdAt.desc()) // 최신순 정렬
                .limit(size + 1) // 다음 페이지 여부 확인을 위해 size + 1개 가져오기
                .fetch();

        boolean hasNext = items.size() > size; // 다음 페이지 존재 여부 확인

        if (hasNext) {
            items.remove(items.size() - 1); // 마지막 요소 제거하여 응답 크기 맞추기
        }

        return new SliceImpl<>(items, PageRequest.of(0, size), hasNext);
    }


    @Override
    public List<Long> closeExpiredBoards() {

        List<Long> expiredBoardIds = queryFactory
                .select(qBoard.id)
                .from(qBoard)
                .where(
                        qBoard.status.eq(BoardStatus.ACTIVE),
                        isExpired(qBoard.endDate)
                )
                .fetch();

        queryFactory
                .update(qBoard)
                .set(qBoard.status, BoardStatus.INACTIVE)
                .where(qBoard.id.in(expiredBoardIds))
                .execute();

        return expiredBoardIds;
    }

    @Override
    public List<Long> activateBoards() {

        List<Long> pendingBoardIds = queryFactory
                .select(qBoard.id)
                .from(qBoard)
                .where(
                        qBoard.status.eq(BoardStatus.PENDING),
                        isExpired(qBoard.startDate)
                )
                .fetch();

        queryFactory
                .update(qBoard)
                .set(qBoard.status, BoardStatus.ACTIVE)
                .where(qBoard.id.in(pendingBoardIds))
                .execute();

        return pendingBoardIds;
    }

    private BooleanExpression isNotDeleted() {
        return qBoard.deletedAt.isNull();
    }

    private BooleanExpression isVoteNotDeleted() {
        return qVote.deletedAt.isNull();
    }

    private BooleanExpression isExpired(DateTimePath<LocalDateTime> dateTimePath) {
        LocalDateTime now = LocalDateTime.now();
        long nowEpochSecond = now.toEpochSecond(ZoneOffset.UTC);
        return Expressions.numberTemplate(
                Long.class,
                "UNIX_TIMESTAMP({0})", dateTimePath
        ).lt(nowEpochSecond);
    }

}
