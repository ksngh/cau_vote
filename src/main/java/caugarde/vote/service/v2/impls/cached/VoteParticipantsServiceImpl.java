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

    @Override
    public VoteParticipants getByBoardId(Long boardId) {
        return voteParticipantsRepository.findByBoardId(boardId).orElse(new VoteParticipants(0));
    }

    @Override
    public void save(Long boardId, VoteParticipants voteParticipants) {
        voteParticipantsRepository.save(boardId, voteParticipants);
    }

    @Override
    public void delete(Long boardId) {
        voteParticipantsRepository.delete(boardId);
    }

    @Override
    public boolean update(Long boardId, VoteParticipants oldVoteParticipants, VoteParticipants newVoteParticipants) {
        return voteParticipantsRepository.update(boardId, oldVoteParticipants, newVoteParticipants);
    }


}
