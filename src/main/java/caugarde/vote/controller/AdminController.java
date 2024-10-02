package caugarde.vote.controller;

import caugarde.vote.model.constant.CustomUserDetails;
import caugarde.vote.model.dto.request.VoteRequestDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/posting")
    public String posting() {
        return "posting";
    }

    @GetMapping("/posting/{id}")
    public String update() {
        return "update";
    }

    @GetMapping("/login")
    public String adminLogin(){
        return "admin-login";
    }
}
