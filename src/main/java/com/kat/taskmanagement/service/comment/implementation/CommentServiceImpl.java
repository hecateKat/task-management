package com.kat.taskmanagement.service.comment.implementation;

import com.kat.taskmanagement.dto.comment.CommentResponseDto;
import com.kat.taskmanagement.dto.comment.CreateCommentRequestDto;
import com.kat.taskmanagement.entity.Comment;
import com.kat.taskmanagement.entity.Task;
import com.kat.taskmanagement.entity.User;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.CommentMapper;
import com.kat.taskmanagement.repository.CommentRepository;
import com.kat.taskmanagement.repository.TaskRepository;
import com.kat.taskmanagement.repository.UserRepository;
import com.kat.taskmanagement.service.comment.CommentService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentResponseDto> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskIdOrderByTimestampAsc(taskId).stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(CreateCommentRequestDto requestDto, String username) {
        Task task = taskRepository.findById(requestDto.taskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found with id: " + requestDto.taskId()));
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found: " + username));
        Comment comment = commentMapper.toEntity(requestDto);
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setTimestamp(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }
}

