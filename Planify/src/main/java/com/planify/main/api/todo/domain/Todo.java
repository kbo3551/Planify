package com.planify.main.api.todo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.todo.value.TodoStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "todo_list")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private TodoStatus status;

    private LocalDateTime dueDate;

    @Column(updatable = false)
    private LocalDateTime regDt = LocalDateTime.now();

    private LocalDateTime modDt;

    @PreUpdate
    public void preUpdate() {
        this.modDt = LocalDateTime.now();
    }

    @Builder
    public Todo(Member member, String title, String description, TodoStatus status, LocalDateTime dueDate) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }
}