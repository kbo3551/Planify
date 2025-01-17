package com.planify.main.web.member;

import org.springframework.stereotype.Controller;
/* 화면 호출 및 view 처리 Controller */
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MemberController {
	
	@GetMapping("/member/profile")
	public String profile() {
		
		return "/profile/profile";
	}
}
