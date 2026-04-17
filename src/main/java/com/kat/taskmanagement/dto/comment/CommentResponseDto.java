package com.kat.taskmanagement.dto.comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long taskId,
        String authorUsername,
        String text,
        LocalDateTime timestamp
) {}

