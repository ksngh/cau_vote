package caugarde.vote.repository.v2.impls.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import caugarde.vote.repository.v2.interfaces.redis.VoteParticipantsRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteParticipantsRepositoryImpl implements VoteParticipantsRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final VoteParticipantsRedisRepository voteParticipantsRedisRepository;

    @Override
    public Optional<VoteParticipants> findByBoardId(Long boardId) {
        return voteParticipantsRedisRepository.findById(String.valueOf(boardId));
    }

    @Override
    public void create(Long boardId) {
        VoteParticipants voteParticipants = VoteParticipants.create(boardId);
        voteParticipantsRedisRepository.save(voteParticipants);
    }

    @Override
    public void delete(Long boardId) {
        voteParticipantsRedisRepository.deleteById(String.valueOf(boardId));
    }

    @Override
    public Long incrementVoteCount(Long boardId) {
        return redisTemplate.opsForValue().increment(String.valueOf(boardId));
    }

    @Override
    public void decrementVoteCount(Long boardId) {
        redisTemplate.opsForValue().decrement(String.valueOf(boardId));
    }

}
