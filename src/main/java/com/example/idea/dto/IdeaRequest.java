package com.example.idea.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String difficulty;

    private String techStack;

}
