package com.planify.main.api.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean isNotice;

    @Column(nullable = false)
    private LocalDateTime sentDt;

    @Column(nullable = false)
    private LocalDateTime regDt;

    @Builder
    public ChatMessage(Long id, String sender, String content, boolean isNotice, LocalDateTime sentDt, LocalDateTime regDt) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.isNotice = isNotice;
        this.sentDt = sentDt;
        this.regDt = regDt;
    }
}