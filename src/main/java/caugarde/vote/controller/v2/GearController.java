package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GearController {

    @GetMapping("/gear")
    public String gear() {
        return "gear/gear_index";
    }

    @GetMapping("/gear/new")
    public String gearCreate(){
        return "gear/gear_new";
    }

    @GetMapping("/gear/update/{gearId}")
    public String gearUpdate(){
        return "gear/gear_update";
    }

}
