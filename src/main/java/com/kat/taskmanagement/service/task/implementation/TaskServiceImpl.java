package com.kat.taskmanagement.service.task.implementation;

import com.kat.taskmanagement.dto.task.CreateTaskRequestDto;
import com.kat.taskmanagement.dto.task.TaskResponseDto;
import com.kat.taskmanagement.entity.Project;
import com.kat.taskmanagement.entity.Task;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.TaskMapper;
import com.kat.taskmanagement.repository.ProjectRepository;
import com.kat.taskmanagement.repository.TaskRepository;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.task.TaskService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskResponseDto> getTasksByProject(Long projectId, Pageable pageable) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        return taskMapper.toDto(findById(id));
    }

    @Override
    @Transactional
    public TaskResponseDto createTask(CreateTaskRequestDto requestDto) {
        Task task = taskMapper.toEntity(requestDto);
        task.setProject(resolveProject(requestDto.projectId()));
        if (requestDto.assigneeId() != null) {
            task.setAssignee(resolveUser(requestDto.assigneeId()));
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, CreateTaskRequestDto requestDto) {
        Task task = findById(id);
        taskMapper.updateTaskFromDto(task, requestDto);
        task.setProject(resolveProject(requestDto.projectId()));
        task.setAssignee(requestDto.assigneeId() != null
                ? resolveUser(requestDto.assigneeId()) : null);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = findById(id);
        task.setDeleted(true);
        taskRepository.save(task);
    }

    private Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found with id: " + id));
    }

    private Project resolveProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Project not found with id: " + projectId));
    }

    private User resolveUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + userId));
    }
}

