package com.example.idea.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaResponse {

    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String techStack;
    private String createdBy;
    private LocalDateTime createdAt;
}
