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
public class BoardDTO {
    private String id;
    private String name;
    private String projectId;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
