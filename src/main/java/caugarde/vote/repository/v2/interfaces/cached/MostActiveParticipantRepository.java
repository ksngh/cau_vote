package caugarde.vote.repository.v2.interfaces.cached;

import caugarde.vote.model.entity.cached.MostActiveParticipant;

import java.util.List;

public interface MostActiveParticipantRepository {

    List<MostActiveParticipant> findMostActiveParticipants();

    void saveMostActiveParticipant(String participantKey, MostActiveParticipant participant);

    void clearAllCache();

}
