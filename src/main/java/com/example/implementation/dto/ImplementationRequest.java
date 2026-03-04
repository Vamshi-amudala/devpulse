package com.example.implementation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImplementationRequest {

    @NotBlank
    private String githubUrl;

    private String repoName;
}