package com.example.final_project.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.final_project.model.Role;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldFindRoleByRoleName() {

        Optional<Role> foundRoleUser = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER);
        Optional<Role> foundRoleAdmin = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_ADMIN);

        assertThat(foundRoleUser).isPresent();
        AssertionsForInterfaceTypes.assertThat(foundRoleUser.get().getRoleName()).isEqualTo(Role.RoleName.ROLE_USER);

        assertThat(foundRoleAdmin).isPresent();
        AssertionsForInterfaceTypes.assertThat(foundRoleAdmin.get().getRoleName()).isEqualTo(Role.RoleName.ROLE_ADMIN);
    }


    @Test
    void shouldDeleteRole() {
        Role roleToDelete = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER).orElseThrow();
        roleRepository.delete(roleToDelete);

        Optional<Role> foundRole = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER);
        assertThat(foundRole).isEmpty();
    }
}