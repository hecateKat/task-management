package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.task.CreateTaskRequestDto;
import com.kat.taskmanagement.dto.task.TaskResponseDto;
import com.kat.taskmanagement.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {

    @Mapping(target = "project",  ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "deleted",  ignore = true)
    Task toEntity(CreateTaskRequestDto requestDto);

    @Mapping(target = "projectId",        source = "project.id")
    @Mapping(target = "assigneeId",       source = "assignee.id")
    @Mapping(target = "assigneeUsername", source = "assignee.username")
    TaskResponseDto toDto(Task task);

    @Mapping(target = "project",  ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "deleted",  ignore = true)
    Task updateTaskFromDto(@MappingTarget Task task, CreateTaskRequestDto requestDto);
}

