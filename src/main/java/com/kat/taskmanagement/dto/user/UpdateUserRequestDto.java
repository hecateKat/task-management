package com.kat.taskmanagement.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDto(
        @NotBlank
        String username,
        @NotBlank @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {}

