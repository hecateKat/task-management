package com.kat.taskmanagement.dto.task;

import com.kat.taskmanagement.entity.Priority;
import com.kat.taskmanagement.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateTaskRequestDto(
        @NotBlank String name,
        String description,
        @NotNull Priority priority,
        @NotNull TaskStatus status,
        LocalDateTime dueDate,
        @NotNull Long projectId,
        Long assigneeId
) {}

