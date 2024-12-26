package com.planify.main.api.todo.presentation;

import org.springframework.http.ResponseEntity;
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

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoRestController {
	
	private final TodoService todoService;

    @GetMapping("/todos/member/{memberNo}")
    public ResponseEntity<List<Todo>> getTodosByMember(@PathVariable Long memberNo) {
        return ResponseEntity.ok(todoService.getAllTodosByMember(memberNo));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.createTodo(todo));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, updatedTodo));
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
        return ResponseEntity.noContent().build();
    }
}
