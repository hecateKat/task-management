package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.comment.CommentResponseDto;
import com.kat.taskmanagement.dto.comment.CreateCommentRequestDto;
import com.kat.taskmanagement.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "task",      ignore = true)
    @Mapping(target = "author",    ignore = true)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "deleted",   ignore = true)
    Comment toEntity(CreateCommentRequestDto requestDto);
    @Mapping(target = "taskId",         source = "task.id")
    @Mapping(target = "authorUsername", source = "author.username")
    CommentResponseDto toDto(Comment comment);
}

