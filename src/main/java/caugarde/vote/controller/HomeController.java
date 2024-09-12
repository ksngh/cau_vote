package caugarde.vote.controller;

import caugarde.vote.model.entity.Student;
import caugarde.vote.service.StudentService;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Security;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final StudentService studentService;

    @GetMapping("/poll")
    public String test(@AuthenticationPrincipal OAuth2User user) {

        return "polling";
    }
}