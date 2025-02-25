package caugarde.vote.repository.v2.interfaces;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository {

    void save(Vote vote);

    Long countVoteByBoard(Board board);

    Optional<Vote> findVoteByBoardAndStudent(Board board, Student student);

    List<Vote> findByBoard(Board board);

}