package com.example.admin.service;

import java.util.List;

import com.example.idea.dto.IdeaResponse;
import com.example.user.dto.UserResponse;

public interface AdminService {

    // User management
    List<UserResponse> getAllUsers();

    void deleteUser(Long userId);

    // Idea management
    List<IdeaResponse> getAllIdeas();

    void deleteIdea(Long ideaId);

    // Implementation management
    void deleteImplementation(Long implementationId);
}
