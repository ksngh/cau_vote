package caugarde.vote.repository.v2.impls.cached;

import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteParticipantsRepositoryImpl implements VoteParticipantsRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String voteParticipantsKey = "voteParticipants:";

    private String getKey(Long boardId) {
        return "voteParticipants:" + boardId;
    }

    @Override
    public Optional<VoteParticipants> findByBoardId(Long boardId) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(voteParticipantsKey + boardId);

        if (entries.isEmpty()) {
            return Optional.empty();
        }

        VoteParticipants voteParticipants = VoteParticipants.of(
                boardId,
                (Integer) entries.getOrDefault("participantsCount", 0)
        );

        return Optional.of(voteParticipants);
    }

    @Override
    public void create(Long boardId) {
        Map<String, Object> hashValues = new HashMap<>();
        hashValues.put(getKey(boardId), boardId);
        hashValues.put("participantsCount",0);
        redisTemplate.opsForHash().putAll(getKey(boardId), hashValues);
    }

    @Override
    public void delete(Long boardId) {
        redisTemplate.delete(getKey(boardId)); // Hash Key 전체 삭제
    }

    @Override
    public Long incrementVoteCount(Long boardId) {
        return redisTemplate.opsForHash().increment(getKey(boardId), "participantsCount", 1);
    }

    @Override
    public void decrementVoteCount(Long boardId) {
        redisTemplate.opsForHash().increment(getKey(boardId), "participantsCount", -1);
    }

}
