package caugarde.vote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoteController {

    @GetMapping("/mypage")
    public String myVotes(){
        return "mypage";
    }
}
