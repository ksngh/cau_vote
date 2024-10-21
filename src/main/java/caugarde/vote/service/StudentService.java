package caugarde.vote.service;

import caugarde.vote.exception.UserNotFoundException;
import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.dto.request.StudentRequestDTO;
import caugarde.vote.model.dto.response.StudentResponseDTO;
import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.jpa.AdminRepository;
import caugarde.vote.repository.jpa.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

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

        studentRepository.save(student);
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

    public StudentResponseDTO userToStudentResponseDTO(Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getAuth()))) {
            return new StudentResponseDTO(authentication.getName(), Role.ADMIN.getAuth());
        } else if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.USER.getAuth())))  {


            return new StudentResponseDTO(authentication.getName(), Role.USER.getAuth());
        } else {
            throw new UserNotFoundException("사용자 권한이 없습니다.");
        }
    }

    public List<Object[]> getAttendanceList() {
        return findVoteCountByStudent();
    }

    public List<Object[]> findVoteCountByStudent(){
        return studentRepository.findVoteCountByStudent();
    }


}
