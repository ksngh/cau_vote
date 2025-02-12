package caugarde.vote.service.v2.impls;

import caugarde.vote.common.exception.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.model.enums.FencingType;
import caugarde.vote.model.enums.VoteAction;
import caugarde.vote.repository.v2.interfaces.VoteRepository;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.VoteService;
import caugarde.vote.service.v2.interfaces.cached.VoteParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void create(Long boardId, FencingType fencingType, String email) {
        Student student = studentService.getByEmail(email);
        Board board = boardService.getById(boardId);
        voteParticipantsService.vote(boardId,board.getLimitPeople());
        Vote vote = Vote.of(student, board, fencingType);
        voteRepository.save(vote);
    }

    @Override
    @Transactional(readOnly = true)
    public Vote getById(Long id) {
        return voteRepository.findById(id).orElseThrow(() -> new CustomApiException(ResErrorCode.NOT_FOUND, "해당하는 투표가 없습니다."));
    }

    @Override
    public Integer getVoteCount(Long boardId) {
        Optional<VoteParticipants> participants = voteParticipantsRepository.findByBoardId(boardId);
        if (participants.isEmpty()) {
            return countVoteByBoardId(boardId);
        }else{
            return participants.get().getParticipantsCount();
        }
    }

    private Integer countVoteByBoardId(Long boardId){
        Board board = boardService.getById(boardId);
        return Math.toIntExact(voteRepository.countVoteByBoard(board));
    }

    @Override
    @Transactional
    public void delete(Long voteId) {
        Vote vote = getById(voteId);
        vote.softDelete();
        voteParticipantsService.cancel(vote.getBoard().getId());
        voteRepository.save(vote);
    }

}
