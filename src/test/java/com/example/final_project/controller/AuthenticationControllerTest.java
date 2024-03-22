package com.example.final_project.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.final_project.dto.user.UserLoginRequestDto;
import com.example.final_project.dto.user.UserLoginResponseDto;
import com.example.final_project.dto.user.UserRegistrationRequestDto;
import com.example.final_project.dto.user.UserResponseDto;
import com.example.final_project.model.User;
import com.example.final_project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }


    @Test
    void loginShouldReturnToken() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        UserLoginRequestDto loginRequest = new UserLoginRequestDto("user@example.com", "password");

        ResponseEntity<UserLoginResponseDto> response = restTemplate.postForEntity("/auth/login",
                loginRequest, UserLoginResponseDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).token()).isNotNull();
    }

    @Test
    void registerShouldReturnUser() {
        String uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
        UserRegistrationRequestDto registrationRequest = new UserRegistrationRequestDto(uniqueEmail,
                "password", "password", "John", "Doe");

        ResponseEntity<UserResponseDto> response = restTemplate.postForEntity("/auth/register",
                registrationRequest, UserResponseDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().email()).isEqualTo(uniqueEmail);
        assertThat(response.getBody().name()).isEqualTo("John");
        assertThat(response.getBody().lastName()).isEqualTo("Doe");
    }
}
