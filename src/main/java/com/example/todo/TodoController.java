package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    private TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{reqId}")
    private ResponseEntity<Todo> findById(@PathVariable long reqId) {
        return ResponseEntity.ok(new Todo(reqId, "test", false));
    }

    @PostMapping
    private ResponseEntity<Void> addNewTodo(@RequestBody Todo newTodoRequest, UriComponentsBuilder ucb) {
       Todo savedTodo = todoRepository.save(newTodoRequest);
       URI locationOfNewCashCard = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.id())
                .toUri();
       return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
