package com.kat.taskmanagement.dto.attachment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAttachmentRequestDto(
        @NotNull Long taskId,
        @NotBlank String dropboxFileId,
        @NotBlank String fileName
) {}

