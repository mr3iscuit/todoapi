package com.biscuit.todo.todo;

import com.biscuit.todo.exception.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo saveTodo(Todo newTodo) {
        return todoRepository.save(newTodo);
    }

    public Todo update(Long id, TodoPatchDTO todoDetails) throws NotImplementedException {
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

    public Todo getTodoById(Long id) throws TodoNotFoundException {
        Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isEmpty()) {
            throw new TodoNotFoundException("Not Todo found with ID:" + id);
        }

        return todo.get();
    }

    public void deleteTodo(Long id) throws NotImplementedException {
        throw new NotImplementedException("deleteTodo Method not implemeted yet.");
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
