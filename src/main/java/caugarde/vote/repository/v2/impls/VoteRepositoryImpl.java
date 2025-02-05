package caugarde.vote.repository.v2.impls;

import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.v2.interfaces.VoteRepository;
import caugarde.vote.repository.v2.interfaces.jpa.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<Vote> findById(Long id) {
        return voteJpaRepository.findById(id);
    }

}
