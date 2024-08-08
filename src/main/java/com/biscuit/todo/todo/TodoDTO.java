package com.biscuit.todo.todo;

import com.biscuit.todo.enums.Importance;

public class TodoDTO {
    private String title;
    private Boolean completed;
    private Importance importance;

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance i) {
        this.importance = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
