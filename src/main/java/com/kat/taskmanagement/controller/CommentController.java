package com.kat.taskmanagement.controller;
import com.kat.taskmanagement.dto.comment.CommentResponseDto;
import com.kat.taskmanagement.dto.comment.CreateCommentRequestDto;
import com.kat.taskmanagement.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Comments", description = "Task comments")
public class CommentController {
    private final CommentService commentService;
    @GetMapping
    @Operation(summary = "Get comments by task ID")
    public List<CommentResponseDto> getCommentsByTask(@RequestParam Long taskId) {
        return commentService.getCommentsByTask(taskId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a comment to a task")
    public CommentResponseDto addComment(Authentication auth,
                                         @RequestBody @Valid CreateCommentRequestDto requestDto) {
        return commentService.addComment(requestDto, auth.getName());
    }
}
