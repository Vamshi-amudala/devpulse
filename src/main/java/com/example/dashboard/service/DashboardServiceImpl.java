package com.example.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dashboard.dto.DashboardResponse;
import com.example.dashboard.dto.DashboardResponse.MyIdeaCard;
import com.example.dashboard.dto.DashboardResponse.MyImplementationCard;
import com.example.dashboard.dto.DashboardResponse.UserInfo;
import com.example.idea.entity.Idea;
import com.example.idea.repository.IdeaRepository;
import com.example.implementation.entity.Implementation;
import com.example.implementation.repository.ImplementationRepository;
import com.example.notification.service.NotificationService;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.vote.repository.VoteRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private ImplementationRepository impRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private NotificationService notificationService;

    @Override
    public DashboardResponse getUserDashboard(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get user info
        UserInfo userInfo = buildUserInfo(user);

        // Get user's ideas with implementation counts
        List<MyIdeaCard> myIdeas = buildMyIdeaCards(user);

        // Get user's implementations with vote counts
        List<MyImplementationCard> myImplementations = buildMyImplementationCards(user);

        // Get recent notifications
        var recentNotifications = notificationService.getRecentNotifications(userId, 10);

        // Get unread count
        long unreadCount = notificationService.getUnreadCount(userId);

        return DashboardResponse.builder()
                .userInfo(userInfo)
                .myIdeas(myIdeas)
                .myImplementations(myImplementations)
                .recentNotifications(recentNotifications)
                .unreadNotificationCount(unreadCount)
                .build();
    }

    private UserInfo buildUserInfo(User user) {
        Long ideaCount = ideaRepo.countByCreatedBy(user);
        Long impCount = impRepo.countBySubmittedBy(user);

        return UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .totalIdeasCreated(ideaCount)
                .totalImplementationsSubmitted(impCount)
                .joinedAt(user.getCreatedAt())
                .build();
    }

    private List<MyIdeaCard> buildMyIdeaCards(User user) {
        List<Idea> ideas = ideaRepo.findByCreatedByOrderByCreatedAtDesc(user);

        return ideas.stream()
                .map(idea -> {
                    int implCount = idea.getImplementations().size();
                    long totalUpvotes = idea.getImplementations().stream()
                            .mapToLong(impl -> voteRepo.countByImplementationId(impl.getId()))
                            .sum();

                    return MyIdeaCard.builder()
                            .id(idea.getId())
                            .title(idea.getTitle())
                            .description(idea.getDescription())
                            .difficulty(idea.getDifficulty())
                            .techStack(idea.getTechStack())
                            .implementationCount(implCount)
                            .totalUpvotes(totalUpvotes)
                            .createdAt(idea.getCreatedAt())
                            .build();
                })
                .toList();
    }

    private List<MyImplementationCard> buildMyImplementationCards(User user) {
        List<Implementation> implementations = impRepo.findBySubmittedByOrderByCreatedAtDesc(user);

        return implementations.stream()
                .map(impl -> {
                    long votes = voteRepo.countByImplementationId(impl.getId());

                    return MyImplementationCard.builder()
                            .id(impl.getId())
                            .ideaId(impl.getIdea().getId())
                            .ideaTitle(impl.getIdea().getTitle())
                            .githubUrl(impl.getGithubUrl())
                            .repoName(impl.getRepoName())
                            .stars(impl.getStars())
                            .primaryLanguage(impl.getPrimaryLanguage())
                            .votes((int) votes)
                            .submittedAt(impl.getCreatedAt())
                            .build();
                })
                .toList();
    }
}
