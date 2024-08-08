package com.biscuit.todo.user;

import java.util.ArrayList;
import java.util.List;

import com.biscuit.todo.todo.Todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Todo> resources = new ArrayList<>();

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setResources(final List<Todo> resources) {
        this.resources = resources;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Todo> getResources() {
        return resources;
    }
}
