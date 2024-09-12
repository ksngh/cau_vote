package caugarde.vote.controller.api;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.enums.Role;
import caugarde.vote.model.enums.SuccessMessage;
import caugarde.vote.model.dto.request.StudentRequestDTO;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.AuthorityService;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentRestController {

    private final StudentService studentService;
    private final AuthorityService authorityService;

    @PostMapping()
    public ResponseEntity<?> signUp(@AuthenticationPrincipal CustomOAuthUser user, @RequestBody StudentRequestDTO studentRequestDTO) {

        Authority authority = authorityService.findByRole(Role.USER);

        Student student = Student.builder()
                .studentPk(user.getId())
                .email(user.getEmail())
                .studentId(studentRequestDTO.getStudentId())
                .majority(studentRequestDTO.getMajority())
                .memberType(studentRequestDTO.getMemberType())
                .name(studentRequestDTO.getName())
                .authority(authority)
                .build();

        studentService.save(student);

        return ResponseEntity.ok(new MessageResponseDTO(student + SuccessMessage.CREATE.getMessage()));
    }

}
