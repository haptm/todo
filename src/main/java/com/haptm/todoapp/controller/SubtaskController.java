package com.haptm.todoapp.controller;

import com.haptm.todoapp.dto.SubtaskDto;
import com.haptm.todoapp.model.Subtask;
import com.haptm.todoapp.service.SubtaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Subtask Resource",
        description = "CRUD REST APIs - Create Subtask, Update Subtask, Get Subtask, Get All Subtasks, Delete Subtask"
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/api/v1/user/task/{taskId}")
public class SubtaskController {
    private final SubtaskService subtaskService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get Subtask REST API",
            description = "Get Subtask REST API is used to get all subtasks of a task from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/subtask")
    public ResponseEntity<List<SubtaskDto>> getSubtasks(@Valid @PathVariable Long taskId) {
        List<Subtask> subtasks = subtaskService.getAllSubtasks(taskId);
        List<SubtaskDto> subtaskDtos = subtasks.stream()
                .map(subtask -> modelMapper.map(subtask, SubtaskDto.class))
                .toList();
        return subtaskDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(subtaskDtos);
    }

    @Operation(
            summary = "Create Subtask REST API",
            description = "Create Subtask REST API is used to save subtask in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/subtask")
    public ResponseEntity<SubtaskDto> createSubtask(@Valid @PathVariable Long taskId, @Valid @RequestBody SubtaskDto subtaskDto) {
        Subtask subtask = new Subtask();
        subtask.setTitle(subtaskDto.getTitle());
        return ResponseEntity.ok(modelMapper.map(subtaskService.createSubtask(taskId, subtask), SubtaskDto.class));
    }

    @Operation(
            summary = "Update Subtask REST API",
            description = "Update Subtask REST API is used to update subtask detail in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/subtask")
    public ResponseEntity<SubtaskDto> updateSubtask(@Valid @RequestBody SubtaskDto subtaskDto) {
        Subtask subtask = modelMapper.map(subtaskDto, Subtask.class);
        subtaskService.updateSubtask(subtaskDto.getSubtaskId(), subtask);
        return ResponseEntity.ok(modelMapper.map(subtask, SubtaskDto.class));
    }

    @Operation(
            summary = "Update Subtask REST API",
            description = "Update Subtask REST API is used to update subtask's status in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/subtask/{subtaskId}")
    public ResponseEntity<SubtaskDto> updateStatus(@Valid @PathVariable Long subtaskId) {
        return ResponseEntity.ok(modelMapper.map(subtaskService.updateStatus(subtaskId), SubtaskDto.class));
    }

    @Operation(
            summary = "Delete Subtask REST API",
            description = "Delete Subtask REST API is used to delete a subtask from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/subtask/{subtaskId}")
    public ResponseEntity<String> deleteSubtask(@Valid @PathVariable Long subtaskId) {
        subtaskService.deleteSubtask(subtaskId);
        return ResponseEntity.ok("Subtask deleted successfully");
    }
}
