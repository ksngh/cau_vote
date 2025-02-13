package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GearController {

    @GetMapping("/gear")
    public String gear() {
        return "gear/gear_index";
    }
}
