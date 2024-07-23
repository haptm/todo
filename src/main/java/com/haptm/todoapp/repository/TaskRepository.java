package com.haptm.todoapp.repository;

import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
