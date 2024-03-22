package com.example.final_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.final_project.dto.user.UserRegistrationRequestDto;
import com.example.final_project.dto.user.UserResponseDto;
import com.example.final_project.exception.RegistrationException;
import com.example.final_project.mapper.UserMapper;
import com.example.final_project.model.Role;
import com.example.final_project.model.User;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    private UserRegistrationRequestDto userRegistrationRequestDto;
    private User sampleUser;
    private UserResponseDto sampleUserResponseDto;
    private Role userRole;

    @BeforeEach
    void setUp() {
        userRegistrationRequestDto = new UserRegistrationRequestDto(
                "maria@example.com", "strongpassword",
                "strongpassword", "Maria", "Zhdanova"
        );
        userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(Role.RoleName.ROLE_USER);

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("maria@example.com");
        sampleUser.setName("Maria");
        sampleUser.setLastName("Zhdanova");
        sampleUser.setPassword("encodedPassword");
        sampleUser.setRoles(Set.of(userRole));

        sampleUserResponseDto = new UserResponseDto(
                1L, "maria@example.com", "Maria", "Zhdanova"
        );
    }

    @Test
    public void shouldRegisterUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toModel(any(UserRegistrationRequestDto.class))).thenReturn(sampleUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByRoleName(any(Role.RoleName.class))).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        when(userMapper.toDto(any(User.class))).thenReturn(sampleUserResponseDto);

        UserResponseDto result = userService.register(userRegistrationRequestDto);

        assertNotNull(result);
        assertEquals(sampleUserResponseDto, result);
    }

    @Test
    public void shouldThrowRegistrationExceptionWhenEmailAlreadyUsed() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(sampleUser));

        assertThrows(RegistrationException.class, () -> userService.register(userRegistrationRequestDto));
    }

    @Test
    public void shouldRegisterAdminUser() {
        UserRegistrationRequestDto adminRegistrationRequestDto = new UserRegistrationRequestDto(
                "admin@example.com", "adminpassword", "adminpassword", "Admin", "User"
        );
        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setRoleName(Role.RoleName.ROLE_ADMIN);

        User adminUser = new User();
        adminUser.setId(2L);
        adminUser.setEmail("admin@example.com");
        adminUser.setName("Admin");
        adminUser.setLastName("User");
        adminUser.setPassword("encodedPassword");
        adminUser.setRoles(Set.of(adminRole));

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toModel(any(UserRegistrationRequestDto.class))).thenReturn(adminUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByRoleName(Role.RoleName.ROLE_ADMIN)).thenReturn(adminRole);
        when(userRepository.save(any(User.class))).thenReturn(adminUser);
        when(userMapper.toDto(any(User.class))).thenReturn(new UserResponseDto(
                2L, "admin@example.com", "Admin", "User"
        ));

        UserResponseDto result = userService.register(adminRegistrationRequestDto);

        assertNotNull(result);
        assertTrue(adminUser.getRoles().contains(adminRole));
    }

    @Test
    public void shouldThrowExceptionWhenRoleNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toModel(any(UserRegistrationRequestDto.class))).thenReturn(sampleUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.getRoleByRoleName(Role.RoleName.ROLE_USER)).
                thenThrow(new NoSuchElementException("Role not found"));

        assertThrows(NoSuchElementException.class, () -> userService.register(userRegistrationRequestDto));
    }
}
