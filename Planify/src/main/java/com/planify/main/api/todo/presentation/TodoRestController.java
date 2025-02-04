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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "todo 관리 API", description = "Todo 관련 API")
public class TodoRestController {

    private final TodoService todoService;

    // 회원별 Todo 목록 조회 (DTO로 변환하여 반환)
    @GetMapping("/todos/member")
    @Operation(summary = "todo 목록 조회", description = "현재 로그인된 사용자의 todo 목록을 조회합니다.")
    public ApiResult<List<TodoDTO>> getTodosByMember() {
    	
        List<TodoDTO> todos = todoService.getAllTodosByMember().stream()
                                         .map(TodoDTO::ofEntity)
                                         .collect(Collectors.toList());
        return ApiResult.success(todos);
    }

    // Todo 생성
    @PostMapping("/todo")
    @Operation(summary = "todo 생성", description = "todo 목록을 생성합니다.")
    public ApiResult<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO) {
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        return ApiResult.success("일정이 생성되었습니다.",createdTodo);
    }

    // Todo 수정
    @PutMapping("/todos/{todoId}")
    @Operation(summary = "todo 수정", description = "todo 목록을 수정합니다.")
    public ApiResult<TodoDTO> updateTodo(@PathVariable Long todoId, @RequestBody TodoDTO updatedTodoDTO) {
        TodoDTO updatedTodo = todoService.updateTodo(todoId, updatedTodoDTO);
        return ApiResult.success("일정이 수정되었습니다.",updatedTodo);
    }

    // Todo 조회
    @GetMapping("/todos/{todoId}")
    @Operation(summary = "todo 상세 조회", description = "todo의 내용을 상세 조회합니다.")
    public ApiResult<TodoDTO> getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId)
                .map(todo -> ApiResult.success(TodoDTO.ofEntity(todo)))
                .orElse(ApiResult.failure(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    // Todo 삭제
    @DeleteMapping("/todos/{todoId}")
    @Operation(summary = "todo 삭제", description = "todo 목록을 삭제합니다.")
    public ApiResult<Void> deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
        return ApiResult.success("일정 삭제완료", null);
    }
}
