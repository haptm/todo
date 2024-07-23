package com.haptm.todoapp.dto;

import com.haptm.todoapp.model.Category;
import com.haptm.todoapp.model.Subtask;
import com.haptm.todoapp.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private Long taskId;

    @Size(max = 50)
    @NotNull
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean status;

    private boolean priority;

    private String categoryName;
}
