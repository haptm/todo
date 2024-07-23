package com.haptm.todoapp.service;

import com.haptm.todoapp.exception.TaskNotFoundException;
import com.haptm.todoapp.exception.UserNotFoundException;
import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.model.User;
import com.haptm.todoapp.repository.CategoryRepository;
import com.haptm.todoapp.repository.TaskRepository;
import com.haptm.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private User checkIfUserExists(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findByUser(checkIfUserExists(userId));
    }

    public Task getTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            return taskOptional.get();
        }
        else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public Task createTask(Long userId, String categoryName, Task task) {
        User user = checkIfUserExists(userId);
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        updateCategory(userId, categoryName, task);
        return taskRepository.save(task);
    }

    public Task updateTask(Long userId, Long taskId, String categoryName, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task updatedTask = taskOptional.get();
            updatedTask.setTitle(task.getTitle());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setDueDate(task.getDueDate());
            updateCategory(userId, categoryName, updatedTask);
            updatedTask.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(updatedTask);
        }
        else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    private void updateCategory(Long userId, String categoryName, Task updatedTask) {
        if (categoryName != null) {
            Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                updatedTask.setCategory(category);
            }
            else {
                Category category = new Category(categoryName);
                updatedTask.setCategory(category);
                User user = checkIfUserExists(userId);
                category.setUser(user);
                categoryRepository.save(category);
            }
        }
        else {
            updatedTask.setCategory(null);
        }
    }

    public Task updateStatus(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(!task.isStatus());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }
        else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public Task updatePriority(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setPriority(!task.isPriority());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        }
        else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
