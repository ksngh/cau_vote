package caugarde.vote.controller;

import caugarde.vote.model.entity.Student;
import caugarde.vote.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final StudentService studentService;

    // 홈 페이지에서 학생 입력 폼을 보여줍니다.
    @GetMapping("/") // 기본 경로로 매핑
    public String home(Model model) {
        model.addAttribute("student", new Student());
        return "index"; // 입력 폼 템플릿 이름
    }

    // 학생 정보를 제출하는 페이지로 이동
    @GetMapping("/submit") // 제출 페이지 경로
    public String submit(Model model) {
        model.addAttribute("student", new Student());
        return "signup"; // 제출 템플릿 이름
    }

    // 학생 정보를 처리하는 메서드
    @PostMapping("/student")
    public String submitForm(@ModelAttribute Student student, Model model) {
        log.info("학생 이름: " + student.getName());
        studentService.save(student); // 학생 정보 저장
        model.addAttribute("student", student);
        log.info(student.toString());
        return "signup"; // 제출 후 결과 페이지
    }

    @GetMapping("/test")
    public String test() {
        return "polling";
    }
}