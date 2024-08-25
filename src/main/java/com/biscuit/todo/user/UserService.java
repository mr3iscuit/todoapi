package com.biscuit.todo.user;

import com.biscuit.todo.todo.TodoRepository;
import com.biscuit.todo.todo.TodoEntity;

import java.security.Principal;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {

    private UserRepository userRepository;
    private TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(
            UserRepository ur,
            TodoRepository tr,
            PasswordEncoder pe) {
        this.userRepository = ur;
        this.todoRepository = tr;
        this.passwordEncoder = pe;
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    public List<TodoEntity> getResourcesByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }
}
