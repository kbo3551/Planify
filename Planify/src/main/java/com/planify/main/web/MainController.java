package com.planify.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.planify.main.api.member.domain.Member;
import com.planify.main.config.security.AuthenticatedUserUtil;

@Controller
public class MainController {

	@GetMapping("/")
	public String context() {
	    return "redirect:/login";
	}

	@GetMapping("/main")
	public String main(Model model) {
		Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
        if (authenticatedUser != null) {
        	model.addAttribute("memberNo", authenticatedUser.getMemberNo());
            model.addAttribute("memberId", authenticatedUser.getMemberId());
            model.addAttribute("nickName", authenticatedUser.getNickName());
            model.addAttribute("name", authenticatedUser.getName());
            model.addAttribute("gender", authenticatedUser.getGender().getDisplayName());
        }
	    return "/main";
	}
}
