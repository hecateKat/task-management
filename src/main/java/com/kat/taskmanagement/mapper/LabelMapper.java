package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.label.CreateLabelRequestDto;
import com.kat.taskmanagement.dto.label.LabelResponseDto;
import com.kat.taskmanagement.entity.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Label toEntity(CreateLabelRequestDto requestDto);
    LabelResponseDto toDto(Label label);
    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Label updateLabelFromDto(@MappingTarget Label label, CreateLabelRequestDto requestDto);
}

