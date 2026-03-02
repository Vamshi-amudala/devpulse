package com.example.idea.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdeaRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String difficulty;

    private String techStacks;

}
