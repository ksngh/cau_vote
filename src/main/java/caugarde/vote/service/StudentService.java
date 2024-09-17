package caugarde.vote.service;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.dto.request.StudentRequestDTO;
import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(Student student) {
        studentRepository.save(student);
    }

    public void signUp(CustomOAuthUser user, StudentRequestDTO studentRequestDTO,Authority authority) {

        Student student = Student.builder()
                .studentPk(user.getId())
                .email(user.getEmail())
                .studentId(studentRequestDTO.getStudentId())
                .majority(studentRequestDTO.getMajority())
                .memberType(studentRequestDTO.getMemberType())
                .name(studentRequestDTO.getName())
                .authority(authority)
                .build();

        save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(UUID id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

}
