package com.alphawork.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private String organizationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Set<String> teamIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
