package com.example.dashboard.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.notification.dto.NotificationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private Long totalIdeasCreated;
        private Long totalImplementationsSubmitted;
        private LocalDateTime joinedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyIdeaCard {
        private Long id;
        private String title;
        private String description;
        private String difficulty;
        private String techStack;
        private Integer implementationCount;
        private Long totalUpvotes;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyImplementationCard {
        private Long id;
        private Long ideaId;
        private String ideaTitle;
        private String githubUrl;
        private String repoName;
        private Integer stars;
        private String primaryLanguage;
        private Integer votes;
        private LocalDateTime submittedAt;
    }

    private UserInfo userInfo;
    private List<MyIdeaCard> myIdeas;
    private List<MyImplementationCard> myImplementations;
    private List<NotificationResponse> recentNotifications;
    private Long unreadNotificationCount;
}
