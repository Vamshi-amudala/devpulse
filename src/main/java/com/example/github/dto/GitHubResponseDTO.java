package com.example.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubResponseDTO {

    @JsonProperty("stargazers_count")
    private Integer stargazersCount;

    private String language;

    @JsonProperty("pushed_at")
    private String pushedAt;
}
