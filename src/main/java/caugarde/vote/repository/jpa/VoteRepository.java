package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    long countAllByVotePk(UUID id);

}
