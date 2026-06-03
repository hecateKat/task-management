package com.kat.taskmanagement.controller;

import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.project.ProjectResponseDto;
import com.kat.taskmanagement.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Projects", description = "Project management")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Get all projects for current user")
    public List<ProjectResponseDto> getAllProjects(Authentication auth, Pageable pageable) {
        return projectService.getAllProjects(auth.getName(), pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    public ProjectResponseDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project")
    public ProjectResponseDto createProject(Authentication auth,
                                            @RequestBody @Valid CreateProjectRequestDto requestDto) {
        return projectService.createProject(requestDto, auth.getName());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a project")
    public ProjectResponseDto updateProject(@PathVariable Long id,
                                            @RequestBody @Valid CreateProjectRequestDto requestDto) {
        return projectService.updateProject(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a project (soft delete)")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    //TODO
}
