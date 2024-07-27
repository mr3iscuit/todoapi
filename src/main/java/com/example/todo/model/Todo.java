package com.example.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Todo(
        @Id Long id,
        String title,
        boolean completed) {
}
