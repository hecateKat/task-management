package com.kat.taskmanagement.service.attachment.implementation;

import com.kat.taskmanagement.dto.attachment.AttachmentResponseDto;
import com.kat.taskmanagement.dto.attachment.CreateAttachmentRequestDto;
import com.kat.taskmanagement.entity.Attachment;
import com.kat.taskmanagement.entity.Task;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.AttachmentMapper;
import com.kat.taskmanagement.repository.AttachmentRepository;
import com.kat.taskmanagement.repository.TaskRepository;
import com.kat.taskmanagement.service.attachment.AttachmentService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public List<AttachmentResponseDto> getAttachmentsByTask(Long taskId) {
        return attachmentRepository.findByTaskId(taskId).stream()
                .map(attachmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AttachmentResponseDto addAttachment(CreateAttachmentRequestDto requestDto) {
        Task task = taskRepository.findById(requestDto.taskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found with id: " + requestDto.taskId()));
        Attachment attachment = attachmentMapper.toEntity(requestDto);
        attachment.setTask(task);
        attachment.setUploadDate(LocalDateTime.now());
        return attachmentMapper.toDto(attachmentRepository.save(attachment));
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Attachment not found with id: " + id));
        attachment.setDeleted(true);
        attachmentRepository.save(attachment);
    }
}

