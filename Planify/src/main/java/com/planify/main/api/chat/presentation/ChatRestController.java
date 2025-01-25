package com.planify.main.api.chat.presentation;

import com.planify.main.api.chat.application.ChatService;
import com.planify.main.api.chat.application.dto.ChatMessageDTO;
import com.planify.main.common.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    
    // 메세지 불러오기
    @GetMapping("/messages")
    public List<ChatMessageDTO> getAllMessages() {
        return chatService.getAllMessages();
    }

    //메시지 저장 및 실시간 채팅
    @PostMapping("/messages")
    public ApiResult<ChatMessageDTO> saveAndBroadcastMessage(@RequestBody ChatMessageDTO messageDTO) {
        // 메시지 저장
        ChatMessageDTO savedMessage = chatService.saveMessage(messageDTO);
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
        return ApiResult.success("메시지가 성공적으로 전송되었습니다.", savedMessage);
    }
}