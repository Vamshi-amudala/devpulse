package com.example.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrendingIdeaResponse implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long ideaId;
    private String ideaTitle;
    private String ideaDescription;
    private String difficulty;
    private String techStack;
    private String createdBy;
    private Long totalVotes; // sum of votes across all implementations
    private Integer implCount; // number of implementations
}
