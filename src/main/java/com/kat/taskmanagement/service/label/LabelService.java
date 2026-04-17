package com.kat.taskmanagement.service.label;

import com.kat.taskmanagement.dto.label.CreateLabelRequestDto;
import com.kat.taskmanagement.dto.label.LabelResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LabelService {

    List<LabelResponseDto> getAllLabels(Pageable pageable);
    LabelResponseDto createLabel(CreateLabelRequestDto requestDto);
    LabelResponseDto updateLabel(Long id, CreateLabelRequestDto requestDto);
    void deleteLabel(Long id);
}

