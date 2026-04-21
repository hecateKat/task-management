package com.kat.taskmanagement.controller;
import com.kat.taskmanagement.dto.task.CreateTaskRequestDto;
import com.kat.taskmanagement.dto.task.TaskResponseDto;
import com.kat.taskmanagement.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tasks", description = "Task management")
public class TaskController {
    private final TaskService taskService;
    @GetMapping
    @Operation(summary = "Get tasks by project ID")
    public List<TaskResponseDto> getTasksByProject(@RequestParam Long projectId, Pageable pageable) {
        return taskService.getTasksByProject(projectId, pageable);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task")
    public TaskResponseDto createTask(@RequestBody @Valid CreateTaskRequestDto requestDto) {
        return taskService.createTask(requestDto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a task")
    public TaskResponseDto updateTask(@PathVariable Long id,
                                      @RequestBody @Valid CreateTaskRequestDto requestDto) {
        return taskService.updateTask(id, requestDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a task (soft delete)")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
