package com.planify.main.web.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@GetMapping("/list")
    public String loginPage() {
        return "/notice/notice";
    }

}
