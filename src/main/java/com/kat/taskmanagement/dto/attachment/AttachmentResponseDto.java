package com.kat.taskmanagement.dto.attachment;

import java.time.LocalDateTime;

public record AttachmentResponseDto(
        Long id,
        Long taskId,
        String dropboxFileId,
        String fileName,
        LocalDateTime uploadDate
) {}

