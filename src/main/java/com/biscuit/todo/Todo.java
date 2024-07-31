package com.biscuit.todo;

import org.springframework.data.annotation.Id;

record Todo(@Id Long id, String title, Boolean completed) {
}
