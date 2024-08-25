package com.biscuit.todo.todo;

import com.biscuit.todo.exception.*;
import com.biscuit.todo.user.UserEntity;
import com.biscuit.todo.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public TodoEntity saveTodo(TodoPostDTO newTodoRequest) {
        UserEntity user = getUser();

        if (newTodoRequest.getCompleted() == null || newTodoRequest.getImportance() == null
                || newTodoRequest.getTitle() == null) {
            throw new ResourceValidationException();
        }


        LocalDateTime currentDate = LocalDateTime.now();

        TodoEntity newTodo = TodoEntity
                .builder()
                .importance(newTodoRequest.getImportance())
                .title(newTodoRequest.getTitle())
                .completed(newTodoRequest.getCompleted())
                .user(user)
                .createDate(currentDate)
                .lastModified(currentDate)
                .build();

        return todoRepository.save(newTodo);
    }

    public TodoEntity update(Long id, TodoPostDTO todoDetails) {
        TodoEntity existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        UserEntity user = getUser();

        if (!existingTodo.getUser().equals(user)) {
            throw new AccessDeniedException("User does not have permission to update this todo.");
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

        // Save the updated Todo
        return todoRepository.save(existingTodo);
    }

    public List<TodoGetDTO> getAllTodosForCurrentUser() {

        UserEntity user = getUser(); // Get the current user
        List<TodoEntity> resources = todoRepository.findByUserId(user.getId());

        return resources.stream()
                .map(todo -> TodoGetDTO.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .completed(todo.getCompleted())
                        .importance(todo.getImportance())
                        .createDate(todo.getCreateDate())
                        .lastModified(todo.getLastModified())
                        .build())
                .collect(Collectors.toList());
    }

    public TodoGetDTO getTodoById(Long id) {

        UserEntity user = getUser();
        Optional<TodoEntity> todoOptional = todoRepository.findById(id);

        if (todoOptional.isEmpty() || !todoOptional.get().getUser().equals(user)) {
            throw new TodoNotFoundException("No Todo found with ID: " + id + " for the current user.");
        }

        TodoEntity todo = todoOptional.get();

        return TodoGetDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .completed(todo.getCompleted())
                .importance(todo.getImportance())
                .createDate(todo.getCreateDate())
                .lastModified(todo.getLastModified())
                .build();
    }

    public UserEntity getUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
    }

    public void deleteTodo(Long id) {

        UserEntity user = getUser(); // Get the current user

        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with ID: " + id));

        if (!todo.getUser().equals(user)) {
            throw new AccessDeniedException("User does not have permission to delete this todo.");
        }

        todoRepository.delete(todo);
    }
}
