package com.kat.taskmanagement.service.label.implementation;

import com.kat.taskmanagement.dto.label.CreateLabelRequestDto;
import com.kat.taskmanagement.dto.label.LabelResponseDto;
import com.kat.taskmanagement.entity.Label;
import com.kat.taskmanagement.exception.EntityNotFoundException;
import com.kat.taskmanagement.mapper.LabelMapper;
import com.kat.taskmanagement.repository.LabelRepository;
import com.kat.taskmanagement.service.label.LabelService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public List<LabelResponseDto> getAllLabels(Pageable pageable) {
        return labelRepository.findAll(pageable).stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public LabelResponseDto createLabel(CreateLabelRequestDto requestDto) {
        Label label = labelMapper.toEntity(requestDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    @Transactional
    public LabelResponseDto updateLabel(Long id, CreateLabelRequestDto requestDto) {
        Label label = findById(id);
        labelMapper.updateLabelFromDto(label, requestDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    @Transactional
    public void deleteLabel(Long id) {
        Label label = findById(id);
        label.setDeleted(true);
        labelRepository.save(label);
    }

    private Label findById(Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Label not found with id: " + id));
    }
}

