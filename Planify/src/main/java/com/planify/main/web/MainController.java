package com.planify.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String context() {
	    return "redirect:/login";
	}

	@GetMapping("/main")
	public String main() {
	    return "/main";
	}
}
