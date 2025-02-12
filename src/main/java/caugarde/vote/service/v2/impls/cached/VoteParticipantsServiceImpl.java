package caugarde.vote.service.v2.impls.cached;

import caugarde.vote.common.exception.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import caugarde.vote.service.v2.interfaces.cached.VoteParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteParticipantsServiceImpl implements VoteParticipantsService {

    private final VoteParticipantsRepository voteParticipantsRepository;

    private VoteParticipants getByBoardId(Long boardId) {
        return voteParticipantsRepository.findByBoardId(boardId).orElseThrow(()->new CustomApiException(ResErrorCode.NOT_FOUND,"투표 참여자를 찾을 수 없습니다."));
    }

    @Override
    public void delete(Long boardId) {
        voteParticipantsRepository.delete(boardId);
    }

    @Override
    public void create(Long boardId, VoteParticipants voteParticipants) {
        voteParticipantsRepository.save(boardId, voteParticipants);
    }

    @Override
    public void vote(Long boardId,int limitPeople) {
        Long count = voteParticipantsRepository.incrementVoteCount(boardId);
        if (limitPeople < count){
            voteParticipantsRepository.decrementVoteCount(boardId);
            throw new CustomApiException(ResErrorCode.SERVICE_UNAVAILABLE,"제한 인원을 초과하였습니다.");
        }
    }

    @Override
    public void cancel(Long boardId) {
        validateMinCount(boardId);
        voteParticipantsRepository.decrementVoteCount(boardId);
    }

    private void validateMinCount(Long boardId) {
        if (0 == getByBoardId(boardId).getParticipantsCount()){
            throw new CustomApiException(ResErrorCode.SERVICE_UNAVAILABLE,"투표 내역이 존재하지 않습니다.");
        }
    }

}
