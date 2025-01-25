package com.planify.main.api.chat.application;

import com.planify.main.api.chat.application.dto.ChatMessageDTO;
import com.planify.main.api.chat.domain.ChatMessage;
import com.planify.main.api.chat.infrastructure.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    // 메시지 조회
    public List<ChatMessageDTO> getAllMessages() {
        return chatMessageRepository.findAll()
                .stream()
                .map(ChatMessageDTO::ofEntity) // domain → DTO 변환
                .collect(Collectors.toList());
    }

    // 메시지 저장
    public ChatMessageDTO saveMessage(ChatMessageDTO messageDTO) {
        ChatMessage chatMessage = messageDTO.toEntity();
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        return ChatMessageDTO.ofEntity(savedMessage);
    }
}