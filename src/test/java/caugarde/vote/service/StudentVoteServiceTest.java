package caugarde.vote.service;

import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.jpa.AuthorityRepository;
import caugarde.vote.repository.jpa.StudentRepository;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentVoteServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private StudentVoteRepository studentVoteRepository;

    @Autowired
    private StudentRepository studentRepository;



    @BeforeEach
    void setUp() {
        UUID votePk = UUID.fromString("bba06759-1402-4b8d-9585-a1d1991f802c");
        UUID studentPk = UUID.fromString("aba06759-1402-4b8d-9585-a1d1991f802c");


        Student student = new Student(studentPk,"20241014","test@test.com","수학과","김성호",authorityRepository.findByRole(Role.USER).orElse(null),"기존 회원");
        studentRepository.save(student);

        Vote vote = new Vote(votePk,"title","content",new Timestamp(System.currentTimeMillis()-3600),new Timestamp(System.currentTimeMillis()+3600),3);
        voteRepository.save(vote);

        studentVoteRepository.save(
                StudentVote.builder()
                        .studentVotePk(UUID.randomUUID())
                        .student(student)
                        .vote(vote)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        studentVoteRepository.deleteAll();
        studentRepository.deleteAll();
        voteRepository.deleteAll();
    }

}