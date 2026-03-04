package com.example.implementation.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ImplementationResponse {

    private Long id;
    private Long ideaId;
    private String ideaTitle;
    private String githubUrl;
    private String repoName;
    private Integer stars;
    private String primaryLanguage;
    private String submittedBy;
    private LocalDateTime createdAt;
}