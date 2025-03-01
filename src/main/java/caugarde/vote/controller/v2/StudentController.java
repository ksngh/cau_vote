package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/sign-up")
    public String signup() {
        return "user/sign_up";
    }

    @GetMapping("/mypage/gear")
    public String myPageGear() {
        return "gear/gear_mypage";
    }

    @GetMapping("/mypage/vote")
    public String myPageVote() {
        return "vote/vote_mypage";
    }
}
