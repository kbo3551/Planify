package com.planify.main.web.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticeController {
	
	@GetMapping("/notice")
    public String loginPage() {
        return "/notice/notice";
    }

}
