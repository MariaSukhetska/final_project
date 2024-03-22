package com.example.final_project.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.final_project.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Sql(scripts = {
            "classpath:database/add_users.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/remove_users.sql",
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    @Test
    void findByEmailShouldReturnUser() {
        Optional<User> foundUser = userRepository.findByEmail("superusers@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("superusers@example.com");
    }

    @Test
    void findByEmailShouldReturnEmptyWhenUserDoesNotExist() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistentuser@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldSaveNewUser() {
        User newUser = new User();
        newUser.setEmail("maria_zhdanova@example.com");
        newUser.setName("Maria");
        newUser.setLastName("Zhdanova");
        newUser.setPassword("secure_password");
        newUser.setDeleted(false);

        User savedUser = userRepository.save(newUser);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("maria_zhdanova@example.com");
    }

    @Test
    void shouldDeleteUser() {
        Optional<User> userToDelete = userRepository.findByEmail("superusers@example.com");
        userToDelete.ifPresent(userRepository::delete);

        Optional<User> foundUser = userRepository.findByEmail("superusers@example.com");
        assertThat(foundUser).isEmpty();
    }
}
