package com.biscuit.todo.user;

import com.biscuit.todo.todo.TodoRepository;
import com.biscuit.todo.todo.Todo;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private TodoRepository todoRepository;

    UserService(
            UserRepository ur,
            TodoRepository tr) {
        this.userRepository = ur;
        this.todoRepository = tr;
    }

    public List<Todo> getResourcesByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }
}
