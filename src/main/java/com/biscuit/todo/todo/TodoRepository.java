package com.biscuit.todo.todo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<TodoEntity> findByIdAndUserId(Long id, Long userId);

    // @Query("SELECT t FROM Todo t WHERE t.user.id = :userId")
    List<TodoEntity> findByUserId(Long userId);
}
