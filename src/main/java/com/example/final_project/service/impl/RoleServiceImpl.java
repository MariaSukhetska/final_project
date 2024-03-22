package com.example.final_project.service.impl;

import com.example.final_project.model.Role;
import com.example.final_project.repository.RoleRepository;
import com.example.final_project.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByRoleName(Role.RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Can't find role: " + roleName));
    }
}
