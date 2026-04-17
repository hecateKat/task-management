package com.kat.taskmanagement.service.project.implementation;

import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.project.ProjectResponseDto;
import com.kat.taskmanagement.entity.Project;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.ProjectMapper;
import com.kat.taskmanagement.repository.ProjectRepository;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.project.ProjectService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectResponseDto> getAllProjects(Long userId, Pageable pageable) {
        return projectRepository.findAllByUserId(userId).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        return projectMapper.toDto(findById(id));
    }

    @Override
    @Transactional
    public ProjectResponseDto createProject(CreateProjectRequestDto requestDto, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + userId));
        Project project = projectMapper.toEntity(requestDto);
        project.getUsers().add(owner);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProject(Long id, CreateProjectRequestDto requestDto) {
        Project project = findById(id);
        projectMapper.updateProjectFromDto(project, requestDto);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        Project project = findById(id);
        project.setDeleted(true);
        projectRepository.save(project);
    }

    private Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Project not found with id: " + id));
    }
}

