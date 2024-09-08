package caugarde.vote.repository;

import caugarde.vote.model.entity.StudentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentVoteRepository extends JpaRepository<StudentVote, UUID> {
}
