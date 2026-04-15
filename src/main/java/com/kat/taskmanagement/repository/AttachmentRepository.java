package com.kat.taskmanagement.repository;

import com.kat.taskmanagement.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /** All attachments for a given task. */
    List<Attachment> findByTaskId(Long taskId);

    /** Look up an attachment by its Dropbox file ID. */
    Optional<Attachment> findByDropboxFileId(String dropboxFileId);
}

