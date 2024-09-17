package caugarde.vote.repository;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentVoteRepositoryTest {

    @Autowired
    private StudentVoteRepository studentVoteRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void findByStudentIdAndVoteId() {

    }

    @Test
    void save(){
        Vote vote = voteRepository.findById(UUID.fromString("9a21e8fa-b5f9-4be1-a990-0ea9558b7651")).orElse(null);
    }
}