package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.websocket.CustomWebSocketException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.vote.VoteCreate;
import caugarde.vote.model.dto.vote.VoteInfo;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.model.enums.BoardStatus;
import caugarde.vote.repository.v2.interfaces.VoteRepository;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.VoteService;
import caugarde.vote.service.v2.interfaces.cached.VoteParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final BoardService boardService;
    private final StudentService studentService;
    private final VoteParticipantsService voteParticipantsService;
    private final VoteParticipantsRepository voteParticipantsRepository;

    @Override
    @Transactional
    public void create(Long boardId, VoteCreate.Request request, String email) {
        Student student = studentService.getByEmail(email);
        Board board = boardService.getById(boardId);
        validateExpiredBoard(board);
        validateExistingVote(board, student);
        voteParticipantsService.vote(board);
        Vote vote = Vote.of(student, board, request.getFencingType());
        voteRepository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoteInfo.Response> getByBoardId(Long boardId) {
        List<Vote> votes = voteRepository.findByBoard(boardService.getById(boardId));
        return votes.stream().map(VoteInfo.Response::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getVoteCount(Long boardId) {
        Optional<VoteParticipants> participants = voteParticipantsRepository.findByBoardId(boardId);
        if (participants.isEmpty()) {
            return countVoteByBoardId(boardId);
        } else {
            return participants.get().getParticipantsCount();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Vote getByBoardAndStudent(Long boardId, String email) {
        Board board = boardService.getById(boardId);
        Student student = studentService.getByEmail(email);
        return voteRepository.findVoteByBoardAndStudent(board, student).orElseThrow(() -> new CustomWebSocketException(ResErrorCode.NOT_FOUND, "투표 내역이 존재하지 않습니다."));
    }

    private Integer countVoteByBoardId(Long boardId) {
        Board board = boardService.getById(boardId);
        return Math.toIntExact(voteRepository.countVoteByBoard(board));
    }

    @Override
    @Transactional
    public void delete(Vote vote) {
        vote.softDelete();
        voteParticipantsService.cancel(vote.getBoard().getId());
        voteRepository.save(vote);
    }

    private void validateExpiredBoard(Board board) {
        if (board.getStatus().equals(BoardStatus.INACTIVE)) {
            throw new CustomWebSocketException(ResErrorCode.BAD_REQUEST, "기한이 지난 투표입니다.");
        }
    }

    private void validateExistingVote(Board board, Student student) {
        if (voteRepository.findVoteByBoardAndStudent(board, student).isPresent()) {
            throw new CustomWebSocketException(ResErrorCode.BAD_REQUEST, "이미 참여하신 투표입니다.");
        }
    }

}
