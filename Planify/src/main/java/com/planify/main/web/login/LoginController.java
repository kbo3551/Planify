package com.planify.main.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String loginPage() {
        return "/login.body";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "/register.body";
    }
}
