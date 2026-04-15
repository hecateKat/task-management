package com.kat.taskmanagement.repository;

import com.kat.taskmanagement.entity.Project;
import com.kat.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /** Projects where a given user is a member. */
    List<Project> findByUsersContaining(User user);

    /** Projects where a given user is a member (by user id). */
    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    List<Project> findAllByUserId(@Param("userId") Long userId);
}

