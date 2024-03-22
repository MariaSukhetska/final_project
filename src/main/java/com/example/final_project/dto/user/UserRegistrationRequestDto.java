package com.example.final_project.dto.user;

import com.example.final_project.security.validate.FieldMatch;
import com.example.final_project.security.validate.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords don't match!"
)
public record UserRegistrationRequestDto(
        @Email String email,
        @NotEmpty @PasswordValidator String password,
        String repeatPassword,
        @NotEmpty String name,
        @NotEmpty String lastName
) {}
