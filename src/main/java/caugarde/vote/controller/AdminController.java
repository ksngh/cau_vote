package caugarde.vote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String adminLogin() {
        return "admin-login";
    }
}
