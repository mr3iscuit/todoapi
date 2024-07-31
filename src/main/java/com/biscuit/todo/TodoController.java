package com.biscuit.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long todoId) {
        Todo todo = new Todo(todoId, "Title", false);
        return ResponseEntity.ok(todo);
    }
}