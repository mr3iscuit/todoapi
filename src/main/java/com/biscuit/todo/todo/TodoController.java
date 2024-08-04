package com.biscuit.todo.todo;

import com.biscuit.todo.exception.NotImplementedException;
import com.biscuit.todo.exception.TodoNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    private TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.getTodoById(todoId));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(TodoNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodo() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody Todo newTodoRequest, UriComponentsBuilder ucb)
            throws NotImplementedException {

        Todo savedTodo = todoService.saveTodo(newTodoRequest);

        URI locationOfNewTodo = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Void> updateCashCard(
            @PathVariable Long id,
            @RequestBody TodoPatchDTO todoDetails,
            UriComponentsBuilder ucb) {

        Todo savedTodo = todoService.update(id, todoDetails);

        URI locationOfNewTodo = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }
}
