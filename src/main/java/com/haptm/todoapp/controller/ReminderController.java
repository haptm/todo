package com.haptm.todoapp.controller;

import com.haptm.todoapp.dto.ReminderDto;
import com.haptm.todoapp.model.Reminder;
import com.haptm.todoapp.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Reminder Resource",
        description = "CRUD REST APIs - Create Reminder, Update Reminder, Get Reminder, Delete Reminder"
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("/api/v1/user/task")
public class ReminderController {
    private final ReminderService reminderService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get Reminder REST API",
            description = "Get Reminder REST API is used to reminder of a task from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{taskId}/reminder")
    public ResponseEntity<ReminderDto> getReminder(@PathVariable Long taskId) {
        Reminder reminder = reminderService.getReminder(taskId);
        ReminderDto reminderResponse = modelMapper.map(reminder, ReminderDto.class);
        return ResponseEntity.ok(reminderResponse);
    }

    @Operation(
            summary = "Create Reminder REST API",
            description = "Create Reminder REST API is used to save reminder in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/{taskId}/reminder")
    public ResponseEntity<ReminderDto> createReminder(@PathVariable Long taskId, @RequestBody ReminderDto reminderDto) {
        Reminder reminder = modelMapper.map(reminderDto, Reminder.class);
        reminderService.saveReminder(taskId, reminder);
        ReminderDto reminderResponse = modelMapper.map(reminder, ReminderDto.class);
        return ResponseEntity.ok(reminderResponse);
    }

    @Operation(
            summary = "Update Reminder REST API",
            description = "Update Reminder REST API is used to update reminder detail in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/reminder/{reminderId}")
    public ResponseEntity<ReminderDto> updateReminder(@PathVariable Long reminderId, @RequestBody LocalTime time) {
        Reminder reminder = reminderService.updateReminder(reminderId, time);
        ReminderDto reminderResponse = modelMapper.map(reminder, ReminderDto.class);
        return ResponseEntity.ok(reminderResponse);
    }

    @Operation(
            summary = "Delete Reminder REST API",
            description = "Delete Reminder REST API is used to delete a reminder from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/reminder/{reminderId}")
    public ResponseEntity<String> deleteReminder(@PathVariable Long reminderId) {
        reminderService.deleteReminder(reminderId);
        return ResponseEntity.ok("Reminder deleted successfully");
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleReminder() {
        List<Reminder> reminders = reminderService.getReminderDue();
        for(Reminder reminder : reminders) {
            messagingTemplate.convertAndSend("/topic/reminders", reminder.getTask().getTitle());
        }
    }
}
