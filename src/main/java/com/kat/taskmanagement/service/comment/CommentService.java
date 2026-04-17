package com.kat.taskmanagement.service.comment;

import com.kat.taskmanagement.dto.comment.CommentResponseDto;
import com.kat.taskmanagement.dto.comment.CreateCommentRequestDto;
import java.util.List;

public interface CommentService {

    List<CommentResponseDto> getCommentsByTask(Long taskId);
    CommentResponseDto addComment(CreateCommentRequestDto requestDto, String username);
}

