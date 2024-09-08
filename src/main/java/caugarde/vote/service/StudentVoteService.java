package caugarde.vote.service;

import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.repository.StudentVoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentVoteService {

    private final StudentVoteRepository studentVoteRepository;

    public void save(StudentVote studentVote) {
        studentVoteRepository.save(studentVote);
    }

    public List<StudentVote> findAll() {
        return studentVoteRepository.findAll();
    }

    public StudentVote findById(UUID id) {
        return studentVoteRepository.findById(id).get();
    }

    public void deleteById(UUID id) {
        studentVoteRepository.deleteById(id);
    }
}
