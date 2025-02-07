package caugarde.vote.repository.v2.impls.cached;

import caugarde.vote.common.util.CustomCacheUtil;
import caugarde.vote.model.entity.cached.MostActiveParticipant;
import caugarde.vote.repository.v2.interfaces.cached.MostActiveParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MostActiveParticipantRepositoryImpl implements MostActiveParticipantRepository {

    private final CustomCacheUtil cacheUtil;
    private final Cache<String, MostActiveParticipant> cache =
            cacheUtil.getOrCreateCache("MostActiveParticipantCache",String.class, MostActiveParticipant.class);

    public List<MostActiveParticipant> findMostActiveParticipants() {
        List<MostActiveParticipant> participants = new ArrayList<>();
        cache.forEach(entry -> participants.add(entry.getValue()));
        return participants;
    }

    public void saveMostActiveParticipant(String participantKey,MostActiveParticipant participant) {
        cache.put(participantKey, participant);
    }

    public void clearAllCache() {
        cache.clear();
    }
}
