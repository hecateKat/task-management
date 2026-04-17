package com.kat.taskmanagement.mapper;

import com.kat.taskmanagement.config.MapperConfig;
import com.kat.taskmanagement.dto.user.UpdateUserRequestDto;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "role",      ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted",   ignore = true)
    @Mapping(target = "projects",  ignore = true)
    User toEntity(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "password",  ignore = true)
    @Mapping(target = "role",      ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted",   ignore = true)
    @Mapping(target = "projects",  ignore = true)
    User updateUserFromDto(@MappingTarget User user, UpdateUserRequestDto requestDto);
}

