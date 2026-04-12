package com.example.admin.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlatformStats {
        private Long totalUsers;
        private Long totalIdeas;
        private Long totalImplementations;
        private Long totalVotes;
        private Long activeUsersThisMonth;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCard {
        private Long id;
        private String name;
        private String email;
        private String role;
        private Long ideasCreated;
        private Long implementationsSubmitted;
        private Long upvotesReceived;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopImplementation {
        private Long id;
        private String ideaTitle;
        private String submittedBy;
        private String repoName;
        private Integer stars;
        private String language;
        private Long votes;
        private LocalDateTime submittedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopIdea {
        private Long id;
        private String title;
        private String createdBy;
        private Integer implementationCount;
        private Long totalUpvotes;
        private LocalDateTime createdAt;
    }

    private PlatformStats platformStats;
    private List<UserCard> allUsers;
    private List<TopImplementation> topTrendingImplementations;
    private List<TopIdea> topTrendingIdeas;
}
