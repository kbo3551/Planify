package com.planify.main.api.todo.presentation;

import java.util.List;

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
import com.planify.main.api.todo.domain.Todo;
import com.planify.main.common.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoRestController {
	
	private final TodoService todoService;

    @GetMapping("/todos/member/{memberNo}")
    public ApiResult<List<Todo>> getTodosByMember(@PathVariable Long memberNo) {
        return ApiResult.success(todoService.getAllTodosByMember(memberNo));
    }

    @PostMapping
    public ApiResult<Todo> createTodo(@RequestBody Todo todo) {
        return ApiResult.success(todoService.createTodo(todo));
    }

    @GetMapping("/todos/{todoId}")
    public ApiResult<Todo> getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId)
                .map(todo -> ApiResult.success(todo))
                .orElse(ApiResult.failure(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    @PutMapping("/todos/{todoId}")
    public ApiResult<Todo> updateTodo(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
        return ApiResult.success(todoService.updateTodo(todoId, updatedTodo));
    }

    @DeleteMapping("/todos/{todoId}")
    public ApiResult<Void> deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
        return ApiResult.success("일정 삭제완료", null);
    }
}
