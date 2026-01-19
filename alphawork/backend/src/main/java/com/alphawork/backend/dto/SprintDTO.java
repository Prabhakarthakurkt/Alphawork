package com.alphawork.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SprintDTO {
    private String id;
    private String name;
    private String goal;
    private String boardId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> issueIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
