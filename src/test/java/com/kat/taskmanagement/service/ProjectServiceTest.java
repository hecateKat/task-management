package com.kat.taskmanagement.service;

import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.project.ProjectResponseDto;
import com.kat.taskmanagement.entity.Project;
import com.kat.taskmanagement.entity.Status;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.ProjectMapper;
import com.kat.taskmanagement.repository.ProjectRepository;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.project.implementation.ProjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private final User testUser = buildUser();
    private final Project testProject = buildProject();
    private final ProjectResponseDto testDto =
            new ProjectResponseDto(1L, "Test", "desc", LocalDate.now(), LocalDate.now().plusDays(10), Status.INITIATED);

    @Test
    @DisplayName("getAllProjects – returns list for existing user")
    void getAllProjects_existingUser_returnsList() {
        when(userRepository.findByUsername("jan")).thenReturn(Optional.of(testUser));
        when(projectRepository.findAllByUserId(1L)).thenReturn(List.of(testProject));
        when(projectMapper.toDto(testProject)).thenReturn(testDto);

        List<ProjectResponseDto> result = projectService.getAllProjects("jan", Pageable.unpaged());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Test");
    }

    @Test
    @DisplayName("getAllProjects – throws EntityNotFoundException when user not found")
    void getAllProjects_userNotFound_throwsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getAllProjects("unknown", Pageable.unpaged()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("getProjectById – returns DTO for existing project")
    void getProjectById_existingProject_returnsDto() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(projectMapper.toDto(testProject)).thenReturn(testDto);

        ProjectResponseDto result = projectService.getProjectById(1L);

        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getProjectById – throws EntityNotFoundException when not found")
    void getProjectById_notFound_throwsException() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("createProject – saves project and returns DTO")
    void createProject_valid_savesAndReturnsDto() {
        CreateProjectRequestDto requestDto = new CreateProjectRequestDto(
                "Test", "desc", LocalDate.now(), LocalDate.now().plusDays(10), Status.INITIATED);

        when(userRepository.findByUsername("jan")).thenReturn(Optional.of(testUser));
        when(projectMapper.toEntity(requestDto)).thenReturn(testProject);
        when(projectRepository.save(testProject)).thenReturn(testProject);
        when(projectMapper.toDto(testProject)).thenReturn(testDto);

        ProjectResponseDto result = projectService.createProject(requestDto, "jan");

        assertThat(result.name()).isEqualTo("Test");
        verify(projectRepository).save(testProject);
    }

    @Test
    @DisplayName("deleteProject – sets isDeleted=true and saves")
    void deleteProject_existingProject_softDeletes() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(projectRepository.save(testProject)).thenReturn(testProject);

        projectService.deleteProject(1L);

        assertThat(testProject.isDeleted()).isTrue();
        verify(projectRepository).save(testProject);
    }

    private User buildUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("jan");
        return u;
    }

    private Project buildProject() {
        Project p = new Project();
        p.setId(1L);
        p.setName("Test");
        return p;
    }
}

