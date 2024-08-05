package com.biscuit.todo.todo;

import com.biscuit.todo.exception.NotImplementedException;
import com.biscuit.todo.exception.ResourceValidationException;
import com.biscuit.todo.exception.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
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
