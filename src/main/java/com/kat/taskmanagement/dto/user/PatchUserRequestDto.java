package com.kat.taskmanagement.dto.user;

import jakarta.validation.constraints.Email;

/**
 * Used for PATCH /users/me â€“ all fields are optional (null = no change).
 */
public record PatchUserRequestDto(
        String username,
        @Email String email,
        String firstName,
        String lastName
) {}

