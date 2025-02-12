package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.entity.Board;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.v2.interfaces.VoteRepository;
import caugarde.vote.repository.v2.interfaces.jpa.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteRepositoryImpl implements VoteRepository {

    private final VoteJpaRepository voteJpaRepository;

    @Override
    public void save(Vote vote) {
        voteJpaRepository.save(vote);
    }

    @Override
    public Long countVoteByBoard(Board board) {
        return voteJpaRepository.countVoteByBoard(board);
    }

    @Override
    public Optional<Vote> findVoteByBoardAndStudent(Board board, Student student) {
        return voteJpaRepository.findByBoardAndStudent(board, student);
    }

    @Override
    public List<Vote> findByBoard(Board board) {
        return voteJpaRepository.findByBoardOrderByCreatedAtAsc(board);
    }

}
