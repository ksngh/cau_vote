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

    private final RedisTemplate<String, VoteParticipants> redisTemplate;
    private final VoteParticipantsRedisRepository voteParticipantsRedisRepository;
    private final static String voteCacheKey = "voteParticipants:";

    @Override
    public Optional<VoteParticipants> findByBoardId(Long boardId) {
        return voteParticipantsRedisRepository.findById(getKey(boardId))
                .or(() -> Optional.of(new VoteParticipants(boardId, 0)));
    }

    @Override
    public void save(Long boardId, VoteParticipants voteParticipants) {
        voteParticipantsRedisRepository.save(voteParticipants);
    }

    @Override
    public void delete(Long boardId) {
        voteParticipantsRedisRepository.deleteById(getKey(boardId));
    }

    @Override
    public void incrementVoteCount(Long boardId) {
        redisTemplate.opsForValue().increment(getKey(boardId));
    }

    @Override
    public void decrementVoteCount(Long boardId) {
        redisTemplate.opsForValue().decrement(getKey(boardId));
    }

    private String getKey(Long boardId){
        return voteCacheKey + boardId;
    }

}
