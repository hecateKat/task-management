package com.kat.taskmanagement.service.project;

import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.project.ProjectResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    List<ProjectResponseDto> getAllProjects(String username, Pageable pageable);
    ProjectResponseDto getProjectById(Long id);
    ProjectResponseDto createProject(CreateProjectRequestDto requestDto, String username);
    ProjectResponseDto updateProject(Long id, CreateProjectRequestDto requestDto);
    void deleteProject(Long id);
}
