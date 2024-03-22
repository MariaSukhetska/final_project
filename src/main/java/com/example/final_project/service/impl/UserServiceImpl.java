package com.example.final_project.service.impl;

import com.example.final_project.dto.user.UserRegistrationRequestDto;
import com.example.final_project.dto.user.UserResponseDto;
import com.example.final_project.exception.RegistrationException;
import com.example.final_project.mapper.UserMapper;
import com.example.final_project.model.Role;
import com.example.final_project.model.User;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.service.RoleService;
import com.example.final_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final String ADMIN_USER_EMAIL = "admin";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequestDto.email()).isPresent()) {
            throw new RegistrationException("The email: " + registrationRequestDto.email()
                    + " is already in use. Please choose a different one.");
        }

        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.password()));
        user.setRoles(getUserRoles(registrationRequestDto.email()));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private Set<Role> getUserRoles(String email) {
        Set<Role> roles = new HashSet<>();
        if (email.contains(ADMIN_USER_EMAIL)) {
            Role adminRole = roleService.getRoleByRoleName(Role.RoleName.ROLE_ADMIN);
            roles.add(adminRole);
        }
        Role defaultRole = roleService.getRoleByRoleName(Role.RoleName.ROLE_USER);
        roles.add(defaultRole);
        return roles;
    }
}
