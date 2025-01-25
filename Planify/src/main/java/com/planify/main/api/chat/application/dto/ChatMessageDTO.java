package com.planify.main.api.chat.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planify.main.api.chat.domain.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ChatMessageDTO {

    private Long id;
    private String sender;
    private String content;
    @JsonProperty("isNotice") // JSON 인식
    private boolean isNotice; // 공지 여부
    private String sentDt;
    private String regDt;

    @Builder
    public ChatMessageDTO(Long id, String sender, String content, boolean isNotice, LocalDateTime sentDt, LocalDateTime regDt) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.isNotice = isNotice;
        this.sentDt = sentDt != null ? sentDt.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")) : null;
        this.regDt = regDt != null ? regDt.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")) : null;
    }

    /**
     * 엔티티 → DTO 변환
     * @param chatMessage ChatMessage 엔티티
     * @return ChatMessageDTO
     */
    public static ChatMessageDTO ofEntity(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
                .id(chatMessage.getId())
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .isNotice(chatMessage.isNotice())
                .sentDt(chatMessage.getSentDt())
                .regDt(chatMessage.getRegDt())
                .build();
    }

    /**
     * DTO → 엔티티 변환
     * @return ChatMessage 엔티티
     */
    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .id(this.id)
                .sender(this.sender)
                .content(this.content)
                .isNotice(this.isNotice)
                .sentDt(LocalDateTime.now())
                .regDt(LocalDateTime.now())
                .build();
    }
}
