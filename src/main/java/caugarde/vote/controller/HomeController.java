package caugarde.vote.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

	@GetMapping()
	public String poll() {
		return "polling";
	}

	@GetMapping("/posting")
	public String posting() {
		return "posting";
	}
}