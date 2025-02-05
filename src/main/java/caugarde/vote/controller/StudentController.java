package caugarde.vote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }


}
