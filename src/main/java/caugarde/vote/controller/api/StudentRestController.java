package caugarde.vote.controller.api;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.constant.CustomUserDetails;
import caugarde.vote.model.dto.response.StudentResponseDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentRestController {

    private final StudentService studentService;
    private final AuthorityService authorityService;

    @PostMapping()
    public ResponseEntity<?> signUp(@AuthenticationPrincipal CustomOAuthUser user, @RequestBody StudentRequestDTO studentRequestDTO) {
        studentService.signUp(user, studentRequestDTO, authorityService.findByRole(Role.USER));
        return ResponseEntity.ok(new MessageResponseDTO("student" + SuccessMessage.CREATE.getMessage()));
    }

    @GetMapping()
    public ResponseEntity<?> validateMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(studentService.userToStudentResponseDTO(authentication));
    }

}
