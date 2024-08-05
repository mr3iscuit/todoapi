package com.biscuit.todo.exception;
public class ResourceValidationException extends RuntimeException {
    public ResourceValidationException() {
        super("Required fields are missing.");
    }

    public ResourceValidationException(String message) {
        super(message);
    }
}
