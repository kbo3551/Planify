package com.planify.main.api.todo.application.dto;

import java.time.LocalDateTime;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.todo.domain.Todo;
import com.planify.main.api.todo.value.TodoStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 디폴트 생성자 추가 (빈 생성자 생성)
public class TodoDTO {

    private Long todoId;
    private Long memberNo;
    private String title;
    private String description;
    private TodoStatus status = TodoStatus.PENDING; // 기본값 설정
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    @Builder
    public TodoDTO(Long todoId, Long memberNo, String title, String description, TodoStatus status,
                   LocalDateTime startDate, LocalDateTime endDate, LocalDateTime regDt, LocalDateTime modDt) {
        this.todoId = todoId;
        this.memberNo = memberNo;
        this.title = title;
        this.description = description;
        this.status = (status != null) ? status : TodoStatus.PENDING;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regDt = regDt;
        this.modDt = modDt;
    }

    // Entity -> DTO 변환
    public static TodoDTO ofEntity(Todo todo) {
        return TodoDTO.builder()
            .todoId(todo.getTodoId())
            .memberNo(todo.getMember() != null ? todo.getMember().getMemberNo() : null)
            .title(todo.getTitle())
            .description(todo.getDescription())
            .status(todo.getStatus())
            .startDate(todo.getStartDate())
            .endDate(todo.getEndDate())
            .regDt(todo.getRegDt())
            .modDt(todo.getModDt())
            .build();
    }

    // DTO -> Entity 변환
    public Todo toEntity(Member member) {
        return Todo.builder()
            .member(member)
            .title(this.title)
            .description(this.description)
            .status(this.status)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .build();
    }
}
