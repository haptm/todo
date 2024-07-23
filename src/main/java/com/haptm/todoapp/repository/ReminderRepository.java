package com.haptm.todoapp.repository;

import com.haptm.todoapp.model.Reminder;
import com.haptm.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByTask(Task task);
    List<Reminder> findByTime(LocalTime time);
}
