package com.alphawork.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLogDTO {
    private String id;
    private String issueId;
    private String userId;
    private Integer hoursSpent;
    private String notes;
}
