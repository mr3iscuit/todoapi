package com.biscuit.todo.todo;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.net.URI;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    private TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Optional<Todo>> getTodo(@PathVariable Long todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);

        return ResponseEntity.ok(todo);
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodo() {
        List<Todo> allTodo = todoRepository.findAll();
        return ResponseEntity.ok(allTodo);
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody Todo newTodoRequest, UriComponentsBuilder ucb) {
       Todo savedTodo = todoRepository.save(newTodoRequest);

       URI locationOfNewTodo = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.getId())
                .toUri();

       return ResponseEntity.created(locationOfNewTodo).build();
    }
}
