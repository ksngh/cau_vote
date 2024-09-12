package caugarde.vote.controller.api;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.constant.SuccessMessage;
import caugarde.vote.model.dto.request.StudentRequestDTO;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.dto.response.StudentResponseDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentRestController {

    private final StudentService studentService;

    @PostMapping()
    public ResponseEntity<?> signUp(@AuthenticationPrincipal CustomOAuthUser user, @RequestBody StudentRequestDTO studentRequestDTO) {

        Student student = Student.builder()
                .studentPk(user.getId())
                .email(user.getEmail())
                .studentId(studentRequestDTO.getStudentId())
                .majority(studentRequestDTO.getMajority())
                .memberType(studentRequestDTO.getMemberType())
                .name(studentRequestDTO.getName())
                .role("ROLE_USER")
                .build();

        studentService.save(student);

        return ResponseEntity.ok(new MessageResponseDTO(student + SuccessMessage.CREATE.getMessage()));
    }

}
