package caugarde.vote.repository.jpa;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentVoteRepository extends JpaRepository<StudentVote, UUID> {

    List<StudentVote> findByVote(Vote vote);

    List<StudentVote> findByVoteOrderByCreatedAtAsc(Vote vote);

    Optional<StudentVote> findByVoteAndStudent(Vote vote, Student student);

    void deleteByVoteAndStudent(Vote vote, Student student);

    void deleteByVote(Vote vote);

    List<StudentVote> findByStudent(Student student);
}
