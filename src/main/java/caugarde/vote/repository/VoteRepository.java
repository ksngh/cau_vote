package caugarde.vote.repository;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    List<Vote> findAllByStudentVotes(List<StudentVote> studentVotes);
}
