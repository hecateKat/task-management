package com.kat.taskmanagement.controller;
import com.kat.taskmanagement.dto.auth.LoginRequestDto;
import com.kat.taskmanagement.dto.auth.LoginResponseDto;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.exception.RegistrationException;
import com.kat.taskmanagement.security.JwtUtil;
import com.kat.taskmanagement.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login endpoints")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
    @PostMapping("/login")
    @Operation(summary = "Login and receive a JWT token")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.username(),
                        requestDto.password()
                )
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new LoginResponseDto(token);
    }
}
