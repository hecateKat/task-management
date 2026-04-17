package com.kat.taskmanagement.service.attachment;

import com.kat.taskmanagement.dto.attachment.AttachmentResponseDto;
import com.kat.taskmanagement.dto.attachment.CreateAttachmentRequestDto;
import java.util.List;

public interface AttachmentService {

    List<AttachmentResponseDto> getAttachmentsByTask(Long taskId);
    AttachmentResponseDto addAttachment(CreateAttachmentRequestDto requestDto);
    void deleteAttachment(Long id);
}

