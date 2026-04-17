package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.project.ProjectResponseDto;
import com.kat.taskmanagement.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "users",   ignore = true)
    Project toEntity(CreateProjectRequestDto requestDto);

    ProjectResponseDto toDto(Project project);

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "users",   ignore = true)
    Project updateProjectFromDto(@MappingTarget Project project,
                                 CreateProjectRequestDto requestDto);
}

