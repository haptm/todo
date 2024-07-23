package com.haptm.todoapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long categoryId;

    @Size(max = 20)
    @NotNull
    private String categoryName;

    @NotNull
    private Long userId;
}
