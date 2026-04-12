package com.example.admin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.admin.dto.AdminDashboardResponse;
import com.example.admin.dto.AdminDashboardResponse.PlatformStats;
import com.example.admin.dto.AdminDashboardResponse.TopIdea;
import com.example.admin.dto.AdminDashboardResponse.TopImplementation;
import com.example.admin.dto.AdminDashboardResponse.UserCard;
import com.example.idea.entity.Idea;
import com.example.idea.repository.IdeaRepository;
import com.example.implementation.entity.Implementation;
import com.example.implementation.repository.ImplementationRepository;
// import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.vote.repository.VoteRepository;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private ImplementationRepository impRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Override
    public AdminDashboardResponse getAdminDashboard() {
        // Build platform stats
        PlatformStats stats = buildPlatformStats();

        // Get all users with their stats
        List<UserCard> allUsers = buildUserCards();

        // Get top trending implementations
        List<TopImplementation> topImplementations = buildTopTrendingImplementations();

        // Get top trending ideas
        List<TopIdea> topIdeas = buildTopTrendingIdeas();

        return AdminDashboardResponse.builder()
                .platformStats(stats)
                .allUsers(allUsers)
                .topTrendingImplementations(topImplementations)
                .topTrendingIdeas(topIdeas)
                .build();
    }

    private PlatformStats buildPlatformStats() {
        long totalUsers = userRepo.count();
        long totalIdeas = ideaRepo.count();
        long totalImplementations = impRepo.count();
        long totalVotes = voteRepo.count();

        // Count users who were active in last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long activeUsersThisMonth = userRepo.findAll().stream()
                .filter(u -> u.getCreatedAt().isAfter(thirtyDaysAgo))
                .count();

        return PlatformStats.builder()
                .totalUsers(totalUsers)
                .totalIdeas(totalIdeas)
                .totalImplementations(totalImplementations)
                .totalVotes(totalVotes)
                .activeUsersThisMonth(activeUsersThisMonth)
                .build();
    }

    private List<UserCard> buildUserCards() {
        return userRepo.findAll().stream()
                .map(user -> {
                    long ideasCount = ideaRepo.countByCreatedBy(user);
                    long implCount = impRepo.countBySubmittedBy(user);
                    long totalUpvotes = impRepo.findBySubmittedById(user.getId()).stream()
                            .mapToLong(impl -> voteRepo.countByImplementationId(impl.getId()))
                            .sum();

                    return UserCard.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .role(user.getRole().name())
                            .ideasCreated(ideasCount)
                            .implementationsSubmitted(implCount)
                            .upvotesReceived(totalUpvotes)
                            .createdAt(user.getCreatedAt())
                            .build();
                })
                .sorted((a, b) -> Long.compare(b.getUpvotesReceived(), a.getUpvotesReceived()))
                .toList();
    }

    private List<TopImplementation> buildTopTrendingImplementations() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> rows = voteRepo.findTopImplementations(pageable);

        return rows.stream()
                .map(row -> {
                    Implementation impl = (Implementation) row[0];
                    Long voteCount = (Long) row[1];

                    return TopImplementation.builder()
                            .id(impl.getId())
                            .ideaTitle(impl.getIdea().getTitle())
                            .submittedBy(impl.getSubmittedBy().getName())
                            .repoName(impl.getRepoName())
                            .stars(impl.getStars())
                            .language(impl.getPrimaryLanguage())
                            .votes(voteCount)
                            .submittedAt(impl.getCreatedAt())
                            .build();
                })
                .toList();
    }

    private List<TopIdea> buildTopTrendingIdeas() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> rows = voteRepo.findTopIdeasByVotes(pageable);

        return rows.stream()
                .map(row -> {
                    Idea idea = (Idea) row[0];
                    Long totalVotes = (Long) row[1];
                    Long implCount = (Long) row[2];

                    return TopIdea.builder()
                            .id(idea.getId())
                            .title(idea.getTitle())
                            .createdBy(idea.getCreatedBy().getName())
                            .implementationCount(implCount.intValue())
                            .totalUpvotes(totalVotes)
                            .createdAt(idea.getCreatedAt())
                            .build();
                })
                .toList();
    }
}
