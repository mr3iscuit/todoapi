package com.biscuit.todo.todo;

import com.biscuit.todo.exception.ResourceValidationException;
import com.biscuit.todo.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/todos/")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(userId, id));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(TodoNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getAllTodo(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getAllTodos(userId));
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(
            @PathVariable Long userId,
            @RequestBody TodoDTO newTodoRequest,
            UriComponentsBuilder ucb) {

        Todo savedTodo = todoService.saveTodo(userId, newTodoRequest);

        URI locationOfNewTodo = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }

    @ExceptionHandler(ResourceValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleResourceValidationException(ResourceValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "400");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Void> updateCashCard(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody TodoDTO todoDetails,
            UriComponentsBuilder ucb) {

        Todo savedTodo = todoService.update(userId, id, todoDetails);
        URI locationOfNewTodo = ucb
                .path("todos/{id}")
                .buildAndExpand(savedTodo.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewTodo).build();
    }
}
