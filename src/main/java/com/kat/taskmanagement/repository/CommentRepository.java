package com.kat.taskmanagement.repository;

import com.kat.taskmanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /** All comments for a given task, ordered by timestamp ascending. */
    List<Comment> findByTaskIdOrderByTimestampAsc(Long taskId);

    /** All comments written by a given user. */
    List<Comment> findByAuthorId(Long authorId);
}

