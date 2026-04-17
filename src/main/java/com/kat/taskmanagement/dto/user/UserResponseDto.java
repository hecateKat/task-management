package com.kat.taskmanagement.dto.user;

import com.kat.taskmanagement.entity.Role;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {}

