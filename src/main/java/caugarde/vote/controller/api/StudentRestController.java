package caugarde.vote.controller.api;

import caugarde.vote.model.dto.request.StudentRequestDTO;
import caugarde.vote.model.dto.response.StudentResponseDTO;
import caugarde.vote.model.entity.Student;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/student")
public class StudentRestController {

    private final StudentService studentService;

    @PostMapping()
    public ResponseEntity<?> signUp(StudentRequestDTO studentRequestDTO,@AuthenticationPrincipal OAuth2User user) {
//        Student student = new Student(user.getAttribute("id"));


//        studentService.save();
        return ResponseEntity.ok("");
    }



}
