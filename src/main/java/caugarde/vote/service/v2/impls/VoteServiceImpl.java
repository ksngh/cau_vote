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
import caugarde.vote.service.v2.interfaces.BoardService;
import caugarde.vote.service.v2.interfaces.StudentService;
import caugarde.vote.service.v2.interfaces.VoteService;
import caugarde.vote.service.v2.interfaces.cached.VoteParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final BoardService boardService;
    private final StudentService studentService;
    private final VoteParticipantsService voteParticipantsService;

    @Override
    @Transactional
    public void create(Long boardId, FencingType fencingType, String email) {

        updateCount(boardId, VoteAction.VOTE);
        saveEntity(boardId, fencingType, email);
    }

    @Override
    @Transactional(readOnly = true)
    public Vote getById(Long id) {
        return voteRepository.findById(id).orElseThrow(() -> new CustomApiException(ResErrorCode.NOT_FOUND, "해당하는 투표가 없습니다."));
    }

    private void saveEntity(Long boardId, FencingType fencingType, String email) {
        Student student = studentService.getByEmail(email);
        Board board = boardService.getById(boardId);
        validateVote(board);
        Vote vote = Vote.of(student, board, fencingType);
        voteRepository.save(vote);
    }

    private void updateCount(Long boardId, VoteAction voteAction) {
        boolean updated = false;
        while (!updated) {

        }
    }

    @Override
    public int getVoteCount(Long boardId) {
        return voteParticipantsService.getByBoardId(boardId).getParticipantsCount();
    }

    private void validateVote(Board board) {
        if (board.getLimitPeople() <= getVoteCount(board.getId())) {
            throw new CustomApiException(ResErrorCode.SERVICE_UNAVAILABLE, "투표 인원이 마감되었습니다.");
        }
    }

    @Override
    @Transactional
    public void delete(Long voteId) {
        Vote vote = getById(voteId);
        vote.softDelete();
        updateCount(vote.getBoard().getId(), VoteAction.CANCEL);
        voteRepository.save(vote);
    }


    private void updateByVoteAction(VoteAction voteAction, VoteParticipants voteParticipants) {
        if (voteAction.name().equals(VoteAction.VOTE.name())) {
            voteParticipants.increment();
        } else {
            voteParticipants.decrement();
        }
    }

}
