package com.planify.main.api.todo.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.todo.domain.Todo;
import com.planify.main.api.todo.infrastructure.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodosByMember(Long memberNo) {
        return todoRepository.findAllByMember_MemberNo(memberNo);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> getTodoById(Long todoId) {
        return todoRepository.findById(todoId);
    }

    public Todo updateTodo(Long todoId, Todo updatedTodo) {
        return todoRepository.findById(todoId).map(todo -> {
            return Todo.builder()
                    .member(todo.getMember())
                    .title(updatedTodo.getTitle())
                    .description(updatedTodo.getDescription())
                    .status(updatedTodo.getStatus())
                    .dueDate(updatedTodo.getDueDate())
                    .build();
        }).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
    }

    public void deleteTodoById(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}