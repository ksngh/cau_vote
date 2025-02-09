package caugarde.vote.repository.v2.interfaces.redis;

import caugarde.vote.model.entity.cached.VoteParticipants;
import org.springframework.data.repository.CrudRepository;

public interface VoteParticipantsRedisRepository extends CrudRepository<VoteParticipants, String> {
}
