package com.alphawork.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDTO {
    private String id;
    private String title;
    private String description;
    private String type;
    private String status;
    private String assigneeId;
    private String assigneeName;
    private String projectId;
    private String boardId;
    private String sprintId;
    private Integer estimateHours;
    private Integer timeSpentHours;
    private Integer orderInColumn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
