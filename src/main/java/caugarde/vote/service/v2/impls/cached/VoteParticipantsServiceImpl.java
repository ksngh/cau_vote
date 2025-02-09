package caugarde.vote.service.v2.impls.cached;

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
    public void delete(Long boardId) {
        voteParticipantsRepository.delete(boardId);
    }

    @Override
    public void create(Long boardId, VoteParticipants voteParticipants) {
        voteParticipantsRepository.save(boardId, voteParticipants);
    }

    @Override
    public void vote(Long boardId) {
        voteParticipantsRepository.incrementVoteCount(boardId);
    }

    @Override
    public void cancel(Long boardId) {
        voteParticipantsRepository.decrementVoteCount(boardId);
    }


}
