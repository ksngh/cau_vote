package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @GetMapping("/post")
    public String post() {
        return "vote/vote_post";
    }

    @GetMapping("/post/{boardId}")
    public String update(@PathVariable Long boardId) {
        return "vote/vote_update";
    }
}
