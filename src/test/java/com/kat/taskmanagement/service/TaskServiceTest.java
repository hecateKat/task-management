package com.kat.taskmanagement.service;

import com.kat.taskmanagement.dto.task.CreateTaskRequestDto;
import com.kat.taskmanagement.dto.task.TaskResponseDto;
import com.kat.taskmanagement.entity.Priority;
import com.kat.taskmanagement.entity.Project;
import com.kat.taskmanagement.entity.Task;
import com.kat.taskmanagement.entity.TaskStatus;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.TaskMapper;
import com.kat.taskmanagement.repository.ProjectRepository;
import com.kat.taskmanagement.repository.TaskRepository;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.task.implementation.TaskServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private final Task testTask = buildTask();
    private final TaskResponseDto testDto =
            new TaskResponseDto(1L, "Task1", "desc", Priority.HIGH, TaskStatus.NOT_STARTED, null, 1L, null, null);

    @Test
    @DisplayName("getTasksByProject – returns list of tasks")
    void getTasksByProject_existingProject_returnsList() {
        when(taskRepository.findByProjectId(1L)).thenReturn(List.of(testTask));        when(taskMapper.toDto(testTask)).thenReturn(testDto);

        List<TaskResponseDto> result = taskService.getTasksByProject(1L, Pageable.unpaged());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Task1");
    }

    @Test
    @DisplayName("getTaskById – returns DTO for existing task")
    void getTaskById_existingTask_returnsDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskMapper.toDto(testTask)).thenReturn(testDto);

        TaskResponseDto result = taskService.getTaskById(1L);

        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getTaskById – throws EntityNotFoundException when not found")
    void getTaskById_notFound_throwsException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("createTask – saves task with project and returns DTO")
    void createTask_valid_savesAndReturnsDto() {
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto(
                "Fix bug", "desc", Priority.HIGH, TaskStatus.NOT_STARTED, null, 1L, null);

        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskMapper.toEntity(requestDto)).thenReturn(testTask);
        when(taskRepository.save(testTask)).thenReturn(testTask);
        when(taskMapper.toDto(testTask)).thenReturn(testDto);

        TaskResponseDto result = taskService.createTask(requestDto);

        assertThat(result.name()).isEqualTo("Task1");
        verify(taskRepository).save(testTask);
    }

    @Test
    @DisplayName("deleteTask – sets isDeleted=true and saves")
    void deleteTask_existingTask_softDeletes() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(testTask)).thenReturn(testTask);

        taskService.deleteTask(1L);

        assertThat(testTask.isDeleted()).isTrue();
        verify(taskRepository).save(testTask);
    }

    private Task buildTask() {
        Task t = new Task();
        t.setId(1L);
        t.setName("Task1");
        return t;
    }
}

