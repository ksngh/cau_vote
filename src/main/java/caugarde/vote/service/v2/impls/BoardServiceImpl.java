package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.board.BoardCreate;
import caugarde.vote.model.dto.board.BoardInfo;
import caugarde.vote.model.dto.board.BoardUpdate;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.repository.v2.interfaces.BoardRepository;
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.cached.VoteParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final VoteParticipantsService voteParticipantsService;
    private final BoardRepository boardRepository;
    private final StudentService studentService;

    @Override
    @Transactional
    public void create(BoardCreate.Request request, CustomOAuthUser user) {
        Student student = studentService.getByEmail(user.getName());
        Board board = Board.create(request,student);
        boardRepository.save(board);
        voteParticipantsService.create(board.getId(),new VoteParticipants(board.getId(),0));
    }

    @Override
    @Transactional
    public void update(BoardUpdate.Request request, Long id, String email) {
        Board board = getById(id);
        board.update(request, email);
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void delete(Long id, String email) {
        Board board = getById(id);
        board.onSoftDelete(email);
    }

    @Override
    public Board getById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new CustomApiException(ResErrorCode.NOT_FOUND, "해당하는 투표 게시글을 찾을 수 없습니다."));
    }

    @Override
    public List<BoardInfo.Response> search(Set<BoardStatus> boardStatusSet) {
        return boardRepository.searchBoard(boardStatusSet);
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void closeExpiredBoards() {
        List<Long> boardIds = boardRepository.closeExpiredBoards(LocalDateTime.now());
        boardIds.forEach(voteParticipantsService::delete);
    }

}
