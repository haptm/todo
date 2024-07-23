package com.haptm.todoapp.repository;

import com.haptm.todoapp.model.Subtask;
import com.haptm.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByTask(Task task);
}
