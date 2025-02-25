package caugarde.vote.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @GetMapping("/admin/student")
    public String student() {
        return "admin/student_index";
    }

    @GetMapping("/admin/student/{studentId}")
    public String studentDetails(@PathVariable Long studentId) {
        return "admin/student_details";
    }
}
