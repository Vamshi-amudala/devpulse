package com.example.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.idea.dto.IdeaResponse;
import com.example.idea.repository.IdeaRepository;
import com.example.implementation.repository.ImplementationRepository;
import com.example.user.dto.UserResponse;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private ImplementationRepository impRepo;

    // ─── Users ───────────────────────────────────────────────────────────────

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException(
                        "User not found for id: " + userId));

        // Delete ideas first (cascade deletes their implementations + votes)
        ideaRepo.deleteAll(ideaRepo.findByCreatedById(userId));

        // Delete any remaining implementations submitted by this user (not linked to
        // their own ideas)
        impRepo.deleteAll(impRepo.findBySubmittedById(userId));

        userRepo.delete(user);
    }

    // ─── Ideas ───────────────────────────────────────────────────────────────

    @Override
    public List<IdeaResponse> getAllIdeas() {
        return ideaRepo.findAll().stream()
                .map(idea -> IdeaResponse.builder()
                        .id(idea.getId())
                        .title(idea.getTitle())
                        .description(idea.getDescription())
                        .difficulty(idea.getDifficulty())
                        .techStack(idea.getTechStack())
                        .createdBy(idea.getCreatedBy().getEmail())
                        .createdAt(idea.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public void deleteIdea(Long ideaId) {
        if (!ideaRepo.existsById(ideaId)) {
            throw new com.example.idea.exception.IdeaNotFoundByIDException("Idea not found for id: " + ideaId);
        }
        ideaRepo.deleteById(ideaId); // cascade deletes implementations + votes
    }

    // ─── Implementations ─────────────────────────────────────────────────────

    @Override
    public void deleteImplementation(Long implementationId) {
        if (!impRepo.existsById(implementationId)) {
            throw new com.example.implementation.exception.ImplementationNotFoundException(
                    "Implementation not found for id: " + implementationId);
        }
        impRepo.deleteById(implementationId); // cascade deletes votes
    }
}
