package com.haptm.todoapp.service;

import com.haptm.todoapp.exception.TaskNotFoundException;
import com.haptm.todoapp.model.Subtask;
import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.repository.SubtaskRepository;
import com.haptm.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;
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

    public List<Subtask> getAllSubtasks(Long taskId) {
        return subtaskRepository.findByTask(checkIfTaskExists(taskId));
    }

    public Subtask createSubtask(Long taskId, Subtask subtask) {
        Task task = checkIfTaskExists(taskId);
        subtask.setTask(task);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
        return subtaskRepository.save(subtask);
    }

    public Subtask updateSubtask(Long subtaskId, Subtask subtask) {
        Optional<Subtask> subtaskOptional = subtaskRepository.findById(subtaskId);
        if (subtaskOptional.isPresent()) {
            Subtask subtaskToUpdate = subtaskOptional.get();
            subtaskToUpdate.setTitle(subtask.getTitle());
            Task task = subtaskToUpdate.getTask();
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
            return subtaskRepository.save(subtaskToUpdate);
        }
        else {
            throw new TaskNotFoundException("Subtask with id " + subtaskId + " not found");
        }
    }

    public Subtask updateStatus(Long subtaskId) {
        Optional<Subtask> subtaskOptional = subtaskRepository.findById(subtaskId);
        if (subtaskOptional.isPresent()) {
            Subtask subtaskToUpdate = subtaskOptional.get();
            subtaskToUpdate.setStatus(!subtaskToUpdate.isStatus());
            Task task = subtaskToUpdate.getTask();
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
            return subtaskRepository.save(subtaskToUpdate);
        }
        else {
            throw new TaskNotFoundException("Subtask with id " + subtaskId + " not found");
        }
    }

    public void deleteSubtask(Long subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }
}
