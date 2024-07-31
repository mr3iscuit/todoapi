package com.example.todo.model;

import org.springframework.data.annotation.Id;

public record Todo(@Id Long id, String title, boolean completed) {
}