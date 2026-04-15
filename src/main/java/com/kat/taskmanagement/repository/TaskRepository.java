package com.kat.taskmanagement.repository;

import com.kat.taskmanagement.entity.Priority;
import com.kat.taskmanagement.entity.Task;
import com.kat.taskmanagement.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /** All tasks belonging to a project. */
    List<Task> findByProjectId(Long projectId);

    /** All tasks assigned to a user. */
    List<Task> findByAssigneeId(Long assigneeId);

    /** Tasks by project filtered by status. */
    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);

    /** Tasks by project filtered by priority. */
    List<Task> findByProjectIdAndPriority(Long projectId, Priority priority);
}

