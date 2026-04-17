package com.kat.taskmanagement.dto.task;

import com.kat.taskmanagement.entity.Priority;
import com.kat.taskmanagement.entity.TaskStatus;
import java.time.LocalDateTime;

public record TaskResponseDto(
        Long id,
        String name,
        String description,
        Priority priority,
        TaskStatus status,
        LocalDateTime dueDate,
        Long projectId,
        Long assigneeId,
        String assigneeUsername
) {}

