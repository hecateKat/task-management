package com.kat.taskmanagement.service.task;

import com.kat.taskmanagement.dto.task.CreateTaskRequestDto;
import com.kat.taskmanagement.dto.task.TaskResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    List<TaskResponseDto> getTasksByProject(Long projectId, Pageable pageable);
    TaskResponseDto getTaskById(Long id);
    TaskResponseDto createTask(CreateTaskRequestDto requestDto);
    TaskResponseDto updateTask(Long id, CreateTaskRequestDto requestDto);
    void deleteTask(Long id);
}

