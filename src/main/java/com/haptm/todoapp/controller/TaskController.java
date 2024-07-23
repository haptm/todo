package com.haptm.todoapp.controller;

import com.haptm.todoapp.dto.TaskDto;
import com.haptm.todoapp.model.Task;
import com.haptm.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Task Resource",
        description = "CRUD REST APIs - Create Task, Update Task, Get Task, Get All Tasks, Delete Task"
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/api/v1/user")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get Task REST API",
            description = "Get Task REST API is used to get all tasks of a user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{userId}/task")
    public ResponseEntity<List<TaskDto>> getTask(@Valid @PathVariable Long userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        List<TaskDto> taskDtos = tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .toList();
        return taskDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(taskDtos);
    }

    @Operation(
            summary = "Create Task REST API",
            description = "Create Task REST API is used to save task in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/{userId}/task")
    public ResponseEntity<TaskDto> createTask(@Valid @PathVariable Long userId, @Valid @RequestBody TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCreatedAt(LocalDateTime.now());
        TaskDto taskDtoResponse = modelMapper.map(taskService.createTask(userId, taskDto.getCategoryName(), task), TaskDto.class);
        return ResponseEntity.ok(taskDtoResponse);
    }

    @Operation(
            summary = "Update Task REST API",
            description = "Update Task REST API is used to update task detail in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/task/")
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto, @Valid @PathVariable Long userId) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        TaskDto taskResponse = modelMapper.map(taskService.updateTask(userId, taskDto.getTaskId(), taskDto.getCategoryName(), task), TaskDto.class);
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(
            summary = "Update Task REST API",
            description = "Update Task REST API is used to update task's status in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/task-status/{taskId}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId) {
        TaskDto taskResponse = modelMapper.map(taskService.updateStatus(taskId), TaskDto.class);
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(
            summary = "Update Task REST API",
            description = "Update Task REST API is used to update task's priority in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/task-priority/{taskId}")
    public ResponseEntity<TaskDto> updateTaskPriority(@PathVariable Long taskId) {
        TaskDto taskResponse = modelMapper.map(taskService.updatePriority(taskId), TaskDto.class);
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(
            summary = "Delete Task REST API",
            description = "Delete Task REST API is used to delete a task from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/{userId}/task/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
