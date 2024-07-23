package com.haptm.todoapp.service;

import com.haptm.todoapp.exception.TaskNotFoundException;
import com.haptm.todoapp.model.Reminder;
import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.repository.ReminderRepository;
import com.haptm.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final TaskRepository taskRepository;

    private Task checkIfTaskExists(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            return task.get();
        }
        else {
            throw new TaskNotFoundException("Task with id " + taskId + " not found");
        }
    }

    public Reminder getReminder(Long taskId) {
        Task task = checkIfTaskExists(taskId);
        return reminderRepository.findByTask(task).orElse(null);
    }

    public Reminder saveReminder(Long taskId, Reminder reminder) {
        Task task = checkIfTaskExists(taskId);
        reminder.setTask(task);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
        return reminderRepository.save(reminder);
    }

    public Reminder updateReminder(Long reminderId, LocalTime time) {
        Optional<Reminder> reminder = reminderRepository.findById(reminderId);
        if (reminder.isPresent()) {
            reminder.get().setTime(time);
            Task task = reminder.get().getTask();
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
            return reminderRepository.save(reminder.get());
        }
        return null;
    }

    public void deleteReminder(Long reminderId) {
        reminderRepository.deleteById(reminderId);
    }

    public List<Reminder> getReminderDue() {
        return reminderRepository.findByTime(LocalTime.now());
    }
}
