package com.planify.main.api.todo.infrastructure;

import com.planify.main.api.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByMemberNo(Long memberNo);
}