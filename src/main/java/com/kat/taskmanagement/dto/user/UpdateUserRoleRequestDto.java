package com.kat.taskmanagement.dto.user;

import com.kat.taskmanagement.entity.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequestDto(
        @NotNull Role role
) {}

