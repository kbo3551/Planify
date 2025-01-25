package com.planify.main.web.chat;


import com.planify.main.api.chat.infrastructure.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/chat/topic")
    public String chatView() {
        return "/chat/chat";
    }
}