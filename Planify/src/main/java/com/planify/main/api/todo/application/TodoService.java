package com.planify.main.api.todo.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.todo.application.dto.TodoDTO;
import com.planify.main.api.todo.domain.Todo;
import com.planify.main.api.todo.infrastructure.TodoRepository;
import com.planify.main.config.security.AuthenticatedUserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public List<Todo> getAllTodosByMember () {
    	Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
        return todoRepository.findAllByMember_MemberNo(authenticatedUser.getMemberNo());
    }

    @Transactional
    public TodoDTO createTodo(TodoDTO todoDTO) {
    	Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
        Member member = memberRepository.findById(authenticatedUser.getMemberNo())
            .orElseThrow(() -> new IllegalArgumentException("Invalid memberNo: " + todoDTO.getMemberNo()));

        Todo todo = todoDTO.toEntity(member);
        Todo savedTodo = todoRepository.save(todo);

        return TodoDTO.ofEntity(savedTodo);
    }

    @Transactional
    public TodoDTO updateTodo(Long todoId, TodoDTO todoDTO) {
    	Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid todoId: " + todoId));

        Member member = memberRepository.findById(authenticatedUser.getMemberNo())
            .orElseThrow(() -> new IllegalArgumentException("Invalid memberNo: " + todoDTO.getMemberNo()));

        todo.update(member, todoDTO.getTitle(), todoDTO.getDescription(), todoDTO.getStatus(),
                    todoDTO.getStartDate(), todoDTO.getEndDate());

        return TodoDTO.ofEntity(todo);
    }
    
    public Optional<Todo> getTodoById(Long todoId) {
        return todoRepository.findById(todoId);
    }

    @Transactional
    public void deleteTodoById(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}