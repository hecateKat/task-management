package com.kat.taskmanagement.dto.project;

import com.kat.taskmanagement.entity.Status;
import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Status status
) {}

