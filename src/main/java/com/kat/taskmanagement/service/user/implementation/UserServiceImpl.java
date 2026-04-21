package com.kat.taskmanagement.service.user.implementation;

import com.kat.taskmanagement.dto.user.PatchUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRoleRequestDto;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.entity.Role;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.exception.RegistrationException;
import com.kat.taskmanagement.mapper.UserMapper;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByUsername(requestDto.username())) {
            throw new RegistrationException(
                    "Username already taken: " + requestDto.username());
        }
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException(
                    "Email already registered: " + requestDto.email());
        }
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRole(Role.USER);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getMe(String username) {
        return userMapper.toDto(findByUsername(username));
    }

    @Override
    @Transactional
    public UserResponseDto updateMe(String username, UpdateUserRequestDto requestDto) {
        User user = findByUsername(username);
        userMapper.updateUserFromDto(user, requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto patchMe(String username, PatchUserRequestDto requestDto) {
        User user = findByUsername(username);
        if (requestDto.username() != null) user.setUsername(requestDto.username());
        if (requestDto.email()    != null) user.setEmail(requestDto.email());
        if (requestDto.firstName()!= null) user.setFirstName(requestDto.firstName());
        if (requestDto.lastName() != null) user.setLastName(requestDto.lastName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto updateRole(Long id, UpdateUserRoleRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + id));
        user.setRole(requestDto.role());
        return userMapper.toDto(userRepository.save(user));
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found: " + username));
    }
}

