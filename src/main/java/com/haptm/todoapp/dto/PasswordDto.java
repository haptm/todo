package com.haptm.todoapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDto {
    @NotNull(message = "Password cannot be null")
    private String newPassword;

    @NotNull(message = "Password cannot be null")
    private String currentPassword;
}
