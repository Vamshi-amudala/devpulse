package com.example.implementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.github.service.GithubService;
import com.example.idea.entity.Idea;
import com.example.idea.exception.IdeaNotFoundByIDException;
import com.example.idea.exception.UnauthorizedAccessException;
import com.example.idea.repository.IdeaRepository;
import com.example.implementation.dto.ImplementationRequest;
import com.example.implementation.dto.ImplementationResponse;
import com.example.implementation.entity.Implementation;
import com.example.implementation.exception.ImplementationNotFoundException;
import com.example.implementation.repository.ImplementationRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class ImplementationServiceImpl implements ImplementationService {

        @Autowired
        private ImplementationRepository impRepo;

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private IdeaRepository ideaRepo;

        @Autowired
        private GithubService githubService;

        @Override
        public ImplementationResponse createImplementation(Long ideaId, ImplementationRequest request) {

                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Idea idea = ideaRepo.findById(ideaId)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for id: " + ideaId));

                Implementation impl = Implementation.builder()
                                .githubUrl(request.getGithubUrl())
                                .idea(idea)
                                .submittedBy(user)
                                .build();

                // Fetch details from GitHub API
                githubService.enrichImplementation(impl);

                Implementation saved = impRepo.save(impl);

                return ImplementationResponse.builder()
                                .id(saved.getId())
                                .ideaId(idea.getId())
                                .ideaTitle(idea.getTitle())
                                .githubUrl(saved.getGithubUrl())
                                .repoName(saved.getRepoName())
                                .stars(saved.getStars())
                                .primaryLanguage(saved.getPrimaryLanguage())
                                .submittedBy(user.getEmail())
                                .createdAt(saved.getCreatedAt())
                                .build();
        }

        @Override
        public List<ImplementationResponse> getImplementationsByIdea(Long ideaId) {
                List<Implementation> implementations = impRepo.findByIdeaId(ideaId);

                return implementations.stream()
                                .map(impl -> ImplementationResponse.builder()
                                                .id(impl.getId())
                                                .ideaId(impl.getIdea().getId())
                                                .ideaTitle(impl.getIdea().getTitle())
                                                .githubUrl(impl.getGithubUrl())
                                                .repoName(impl.getRepoName())
                                                .stars(impl.getStars())
                                                .primaryLanguage(impl.getPrimaryLanguage())
                                                .submittedBy(impl.getSubmittedBy().getEmail())
                                                .createdAt(impl.getCreatedAt())
                                                .build())
                                .toList();
        }

        @Override
        public void deleteImplementation(Long id) {
                Implementation impl = impRepo.findById(id)
                                .orElseThrow(() -> new ImplementationNotFoundException(
                                                "Implementation not found for id: " + id));

                String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                if (!impl.getSubmittedBy().getEmail().equals(currentEmail)) {
                        throw new UnauthorizedAccessException("You are not authorized to delete this.");
                }

                impRepo.delete(impl);
        }
}
