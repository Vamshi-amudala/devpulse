package com.example.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrendingResponse {

    private Long implementationId;
    private String githubUrl;
    private String repoName;
    private Integer stars;
    private String primaryLanguage;
    private String submittedBy;
    private String ideaTitle;
    private Long voteCount;
}
