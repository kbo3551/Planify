package com.planify.main.api.todo.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.todo.application.TodoService;
import com.planify.main.api.todo.application.dto.TodoDTO;
import com.planify.main.common.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoRestController {

    private final TodoService todoService;

    // 회원별 Todo 목록 조회 (DTO로 변환하여 반환)
    @GetMapping("/todos/member/{memberNo}")
    public ApiResult<List<TodoDTO>> getTodosByMember(@PathVariable Long memberNo) {
        List<TodoDTO> todos = todoService.getAllTodosByMember(memberNo).stream()
                                         .map(TodoDTO::ofEntity)
                                         .collect(Collectors.toList());
        return ApiResult.success(todos);
    }

    // Todo 생성
    @PostMapping("/todo")
    public ApiResult<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO) {
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        return ApiResult.success(createdTodo);
    }

    // Todo 수정
    @PutMapping("/todos/{todoId}")
    public ApiResult<TodoDTO> updateTodo(@PathVariable Long todoId, @RequestBody TodoDTO updatedTodoDTO) {
        TodoDTO updatedTodo = todoService.updateTodo(todoId, updatedTodoDTO);
        return ApiResult.success(updatedTodo);
    }

    // Todo 조회
    @GetMapping("/todos/{todoId}")
    public ApiResult<TodoDTO> getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId)
                .map(todo -> ApiResult.success(TodoDTO.ofEntity(todo)))
                .orElse(ApiResult.failure(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    // Todo 삭제
    @DeleteMapping("/todos/{todoId}")
    public ApiResult<Void> deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
        return ApiResult.success("일정 삭제완료", null);
    }
}
