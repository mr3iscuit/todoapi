package com.biscuit.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException() {
        super("Todo Not Found!");
    }
    public TodoNotFoundException(String message) {
        super(message);
    }
}
