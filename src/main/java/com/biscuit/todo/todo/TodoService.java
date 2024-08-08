package com.biscuit.todo.todo;

import com.biscuit.todo.exception.*;
import com.biscuit.todo.user.User;
import com.biscuit.todo.user.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private TodoRepository todoRepository;
    private UserRepository userRepository;

    TodoService(
            TodoRepository todoRepository,
            UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public Todo saveTodo(Todo newTodo) {
        if (newTodo.getCompleted() == null || newTodo.getImportance() == null || newTodo.getTitle() == null) {
            throw new ResourceValidationException();
        }

        return todoRepository.save(newTodo);
    }

    public Todo saveTodo(Long userId, TodoDTO newTodoRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveTodo'");
    }

    public Todo update(Long id, TodoDTO todoDetails) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (todoDetails.getTitle() != null) {
            existingTodo.setTitle(todoDetails.getTitle());
        }

        if (todoDetails.getCompleted() != null) {
            existingTodo.setCompleted(todoDetails.getCompleted());
        }

        if (todoDetails.getImportance() != null) {
            existingTodo.setImportance(todoDetails.getImportance());
        }

        // Save the updated Todo
        return todoRepository.save(existingTodo);
    }

    public Todo update(Long userId, Long id, TodoDTO todoDetails) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (existingTodo.getUser().getId() != userId) {
            throw new TodoNotFoundException("No available Todo found for user");
        }

        if (todoDetails.getTitle() != null) {
            existingTodo.setTitle(todoDetails.getTitle());
        }

        if (todoDetails.getCompleted() != null) {
            existingTodo.setCompleted(todoDetails.getCompleted());
        }

        if (todoDetails.getImportance() != null) {
            existingTodo.setImportance(todoDetails.getImportance());
        }

        return todoRepository.save(existingTodo);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<Todo> getAllTodos(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo getTodoById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isEmpty()) {
            throw new TodoNotFoundException("Not Todo found with ID:" + id);
        }

        return todo.get();
    }

    public Todo getTodoById(Long userId, Long id) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException());

        if (todo.getUser() != user) {
            throw new TodoNotFoundException("No available Todo found for user");
        }

        return todo;
    }

    public void deleteTodo(Long id) {
        throw new NotImplementedException("deleteTodo Method not implemeted yet.");
    }
}
