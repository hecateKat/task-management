package com.kat.taskmanagement.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRegistrationRequestDto(
        @NotBlank
        String username,
        @NotBlank @Length(min = 8, max = 64)
        String password,
        @NotBlank @Length(min = 8, max = 64)
        String repeatPassword,
        @NotBlank @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {}

