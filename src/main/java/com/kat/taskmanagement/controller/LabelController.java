package com.kat.taskmanagement.controller;
import com.kat.taskmanagement.dto.label.CreateLabelRequestDto;
import com.kat.taskmanagement.dto.label.LabelResponseDto;
import com.kat.taskmanagement.service.label.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Labels", description = "Label management")
public class LabelController {
    private final LabelService labelService;
    @GetMapping
    @Operation(summary = "Get all labels")
    public List<LabelResponseDto> getAllLabels(Pageable pageable) {
        return labelService.getAllLabels(pageable);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new label")
    public LabelResponseDto createLabel(@RequestBody @Valid CreateLabelRequestDto requestDto) {
        return labelService.createLabel(requestDto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a label")
    public LabelResponseDto updateLabel(@PathVariable Long id,
                                        @RequestBody @Valid CreateLabelRequestDto requestDto) {
        return labelService.updateLabel(id, requestDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a label (soft delete)")
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
