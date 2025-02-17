package caugarde.vote.repository.v2.interfaces.jpa;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteJpaRepository extends JpaRepository<Vote, Long> {

    long countVoteByBoard(Board board);

    Optional<Vote> findByBoardAndStudentAndDeletedAtIsNull(Board board, Student student);

    List<Vote> findByBoardOrderByCreatedAtAsc(Board board);
}
