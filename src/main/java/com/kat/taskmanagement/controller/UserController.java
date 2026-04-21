package com.kat.taskmanagement.controller;
import com.kat.taskmanagement.dto.user.PatchUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRequestDto;
import com.kat.taskmanagement.dto.user.UpdateUserRoleRequestDto;
import com.kat.taskmanagement.dto.user.UserResponseDto;
import com.kat.taskmanagement.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "User profile management")
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public UserResponseDto getMe(Authentication auth) {
        return userService.getMe(auth.getName());
    }
    @PutMapping("/me")
    @Operation(summary = "Update current user profile (full update)")
    public UserResponseDto updateMe(Authentication auth,
                                    @RequestBody @Valid UpdateUserRequestDto requestDto) {
        return userService.updateMe(auth.getName(), requestDto);
    }
    @PatchMapping("/me")
    @Operation(summary = "Partially update current user profile")
    public UserResponseDto patchMe(Authentication auth,
                                   @RequestBody @Valid PatchUserRequestDto requestDto) {
        return userService.patchMe(auth.getName(), requestDto);
    }
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role (ADMIN only)")
    public UserResponseDto updateRole(@PathVariable Long id,
                                      @RequestBody @Valid UpdateUserRoleRequestDto requestDto) {
        return userService.updateRole(id, requestDto);
    }
}
