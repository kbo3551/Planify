package com.planify.main.api.chat.presentation;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.chat.application.ChatService;
import com.planify.main.api.chat.application.dto.ChatMessageDTO;
import com.planify.main.common.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Tag(name = "채팅 API", description = "채팅 관련 API")
public class ChatRestController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    
    // 메세지 불러오기
    @GetMapping("/messages")
    @Operation(summary = "채팅 메시지 조회", description = "모든 채팅 메시지를 조회합니다.")
    public List<ChatMessageDTO> getAllMessages() {
        return chatService.getAllMessages();
    }

    //메시지 저장 및 실시간 채팅
    @PostMapping("/messages")
    @Operation(summary = "채팅 메시지 전송", description = "새로운 채팅 메시지를 저장하고, 실시간으로 전송합니다.")
    public ApiResult<ChatMessageDTO> saveAndBroadcastMessage(@RequestBody ChatMessageDTO messageDTO) {
        // 메시지 저장
        ChatMessageDTO savedMessage = chatService.saveMessage(messageDTO);
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
        return ApiResult.success("메시지가 성공적으로 전송되었습니다.", savedMessage);
    }
}