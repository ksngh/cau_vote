package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GearController {

    @GetMapping("/gear")
    public String gear() {
        return "gear/gear_index";
    }

    @GetMapping("/gear/new")
    public String gearCreate() {
        return "gear/gear_create";
    }

    @GetMapping("/gear/status")
    public String gearStatus() {
        return "gear/gear_status";
    }

    @GetMapping("/gear/history")
    public String gearHistory() {
        return "gear/gear_history";
    }

}
