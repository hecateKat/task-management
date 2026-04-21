package com.kat.taskmanagement.service;

import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.entity.Role;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.exception.RegistrationException;
import com.kat.taskmanagement.mapper.UserMapper;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.user.implementation.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("register – saves user and returns DTO when username and email are unique")
    void register_validRequest_returnsDto() throws RegistrationException {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "jan", "password1", "password1", "jan@test.com", "Jan", "Kowalski");

        User entity = new User();
        entity.setUsername("jan");
        UserResponseDto responseDto = new UserResponseDto(1L, "jan", "jan@test.com", "Jan", "Kowalski", Role.USER);

        when(userRepository.existsByUsername("jan")).thenReturn(false);
        when(userRepository.existsByEmail("jan@test.com")).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(passwordEncoder.encode("password1")).thenReturn("hashed");
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toDto(entity)).thenReturn(responseDto);

        UserResponseDto result = userService.register(dto);

        assertThat(result.username()).isEqualTo("jan");
        verify(userRepository).save(entity);
    }

    @Test
    @DisplayName("register – throws RegistrationException when username is taken")
    void register_duplicateUsername_throwsException() {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "jan", "password1", "password1", "jan@test.com", "Jan", "Kowalski");

        when(userRepository.existsByUsername("jan")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(dto))
                .isInstanceOf(RegistrationException.class)
                .hasMessageContaining("jan");
    }

    @Test
    @DisplayName("getMe – returns DTO for existing user")
    void getMe_existingUser_returnsDto() {
        User user = new User();
        user.setUsername("jan");
        UserResponseDto responseDto = new UserResponseDto(1L, "jan", "jan@test.com", "Jan", "Kowalski", Role.USER);

        when(userRepository.findByUsername("jan")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getMe("jan");

        assertThat(result.username()).isEqualTo("jan");
    }

    @Test
    @DisplayName("getMe – throws EntityNotFoundException when user not found")
    void getMe_userNotFound_throwsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getMe("unknown"))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

