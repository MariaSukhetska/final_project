package com.example.final_project.dto.user;

import com.example.final_project.security.validate.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotEmpty
        @Size(min = 8, max = 20)
        @Email
        String email,
        @PasswordValidator
        String password
) {}
