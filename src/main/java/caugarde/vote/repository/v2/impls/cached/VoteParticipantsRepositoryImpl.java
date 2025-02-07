package caugarde.vote.repository.v2.impls.cached;

import caugarde.vote.common.util.CustomCacheUtil;
import caugarde.vote.model.entity.cached.VoteParticipants;
import caugarde.vote.repository.v2.interfaces.cached.VoteParticipantsRepository;
import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteParticipantsRepositoryImpl implements VoteParticipantsRepository {

    private final CustomCacheUtil cacheUtil;
    private static final String voteCacheKey = "boardId:";
    private final Cache<String,VoteParticipants> cache = 
            cacheUtil.getOrCreateCache("voteCache", String.class, VoteParticipants.class);


    @Override
    public Optional<VoteParticipants> findByBoardId(Long boardId) {
        return Optional.ofNullable(cache.get(voteCacheKey + boardId));
    }

    @Override
    public void save(Long boardId, VoteParticipants voteParticipants) {
        cache.put(voteCacheKey + boardId, voteParticipants);
    }

    @Override
    public void delete(Long boardId) {
        cache.remove(voteCacheKey + boardId);
    }

    @Override
    public boolean update(Long boardId, VoteParticipants oldVoteParticipants, VoteParticipants newVoteParticipants) {
        return cache.replace(voteCacheKey + boardId, oldVoteParticipants, newVoteParticipants);
    }

}
