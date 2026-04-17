package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.attachment.AttachmentResponseDto;
import com.kat.taskmanagement.dto.attachment.CreateAttachmentRequestDto;
import com.kat.taskmanagement.entity.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {

    @Mapping(target = "task",       ignore = true)
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "uploadDate", ignore = true)
    @Mapping(target = "deleted",    ignore = true)
    Attachment toEntity(CreateAttachmentRequestDto requestDto);
    @Mapping(target = "taskId", source = "task.id")
    AttachmentResponseDto toDto(Attachment attachment);
}

