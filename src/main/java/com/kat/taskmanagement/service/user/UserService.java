package com.kat.taskmanagement.service.user;

import com.kat.taskmanagement.dto.user.PatchUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRoleRequestDto;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
    UserResponseDto getMe(String username);
    UserResponseDto updateMe(String username, UpdateUserRequestDto requestDto);
    UserResponseDto patchMe(String username, PatchUserRequestDto requestDto);
    UserResponseDto updateRole(Long id, UpdateUserRoleRequestDto requestDto);
}

