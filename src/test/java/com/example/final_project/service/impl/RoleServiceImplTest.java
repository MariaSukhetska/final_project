package com.example.final_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.final_project.model.Role;
import com.example.final_project.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setRoleName(Role.RoleName.ROLE_USER);
    }

    @Test
    void getRoleByRoleName_ShouldReturnRole() {
        when(roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER)).thenReturn(Optional.of(testRole));
        Role result = roleService.getRoleByRoleName(Role.RoleName.ROLE_USER);
        assertEquals(testRole, result);
    }

    @Test
    void getRoleByRoleName_ShouldThrowException_WhenRoleNotFound() {
        when(roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> roleService.getRoleByRoleName(Role.RoleName.ROLE_USER));
    }

    @Test
    void getRoleByRoleName_ShouldReturnRole_WhenRoleIsAdmin() {
        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setRoleName(Role.RoleName.ROLE_ADMIN);
        when(roleRepository.findRoleByRoleName(Role.RoleName.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        Role result = roleService.getRoleByRoleName(Role.RoleName.ROLE_ADMIN);
        assertEquals(adminRole, result);
    }
}
