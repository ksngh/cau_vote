package caugarde.vote.service;

import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Vote findById(UUID id) {
        return voteRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        voteRepository.deleteById(id);
    }
}