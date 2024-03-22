package com.example.final_project.service;

import com.example.final_project.dto.user.UserRegistrationRequestDto;
import com.example.final_project.dto.user.UserResponseDto;
import com.example.final_project.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException;
}
