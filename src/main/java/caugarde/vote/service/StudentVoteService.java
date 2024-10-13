package caugarde.vote.service;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.dto.response.StudentVoteResponseDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.entity.StudentVote;
import caugarde.vote.model.entity.Vote;
import caugarde.vote.repository.jpa.StudentRepository;
import caugarde.vote.repository.jpa.StudentVoteRepository;
import caugarde.vote.repository.jpa.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentVoteService {

    private final StudentVoteRepository studentVoteRepository;
    private final VoteRepository voteRepository;
    private final StudentRepository studentRepository;

    public Boolean save(UUID id, Student student) {

        Vote vote = voteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(studentVoteRepository.findByVoteAndStudent(vote, student).isPresent()) {
            return false;
        }else{
            StudentVote studentVote = StudentVote.builder()
                    .studentVotePk(UUID.randomUUID())
                    .student(student)
                    .vote(voteRepository.findById(id).orElse(null))
                    .build();

            studentVoteRepository.save(studentVote);
            student.getStudentVotes().add(studentVote);
            studentRepository.save(student);
            return true;
        }

    }

    public List<Student> getStudentsByVoteId(UUID id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vote not found with id: " + id));

        return studentVoteRepository.findByVoteOrderByCreatedAtAsc(vote)
                .stream()
                .map(StudentVote::getStudent)
                .collect(Collectors.toList());
    }

    public List<StudentVoteResponseDTO> studentsToDTOs(List<Student> students) {
        return students.stream().map(StudentVoteResponseDTO::new).collect(Collectors.toList());
    }

    public void deleteByVoteAndStudent(UUID id, CustomOAuthUser user) {
        Student student = studentRepository.findById(user.getId()).orElse(null);
        Vote vote = voteRepository.findById(id).orElse(null);
        studentVoteRepository.deleteByVoteAndStudent(vote, student);
    }

    public List<StudentVote> findAll() {
        return studentVoteRepository.findAll();
    }

    public StudentVote findById(UUID id) {
        return studentVoteRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        studentVoteRepository.deleteById(id);
    }

    public void deleteByVote(UUID id) {
        Vote vote = voteRepository.findById(id).orElse(null);
        studentVoteRepository.deleteByVote(vote);
    }

    public List<StudentVote> getByStudent(Student student) {
        return studentVoteRepository.findByStudent(student);
    }

    public int countByVote(Vote vote){
        return studentVoteRepository.findByVote(vote).size();
    }


}
