package com.example.final_project.dto.user;

public record UserResponseDto(
        Long id,
        String email,
        String name,
        String lastName
) {}
