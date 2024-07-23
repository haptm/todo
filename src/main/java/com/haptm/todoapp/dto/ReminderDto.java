package com.haptm.todoapp.dto;

import com.haptm.todoapp.model.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDto {
    private Long reminderId;

    private Long taskId;

    @NotNull
    private LocalTime time;
}