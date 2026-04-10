package com.example.idea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.idea.dto.IdeaRequest;
import com.example.idea.dto.IdeaResponse;
import com.example.idea.entity.Idea;
import com.example.idea.exception.IdeaNotFoundByIDException;
import com.example.idea.exception.UnauthorizedAccessException;
import com.example.idea.repository.IdeaRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class IdeaServiceImpl implements IdeaService {

        @Autowired
        private IdeaRepository ideaRepo;

        @Autowired
        private UserRepository userRepo;

        @Override
        public IdeaResponse createIdea(IdeaRequest request) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();

                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Idea idea = Idea.builder()
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .difficulty(request.getDifficulty())
                                .techStack(request.getTechStack())
                                .createdBy(user)
                                .build();

                Idea saved = ideaRepo.save(idea);

                return IdeaResponse.builder()
                                .id(saved.getId())
                                .title(saved.getTitle())
                                .description(saved.getDescription())
                                .difficulty(saved.getDifficulty())
                                .techStack(saved.getTechStack())
                                .createdBy(user.getName())
                                .createdAt(saved.getCreatedAt())
                                .build();
        }

        @Override
        public Page<IdeaResponse> getAllIdeas(Pageable pageable) {

                return ideaRepo.findAll(pageable)
                                .map(idea -> IdeaResponse.builder()
                                                .id(idea.getId())
                                                .title(idea.getTitle())
                                                .description(idea.getDescription())
                                                .difficulty(idea.getDifficulty())
                                                .techStack(idea.getTechStack())
                                                .createdBy(idea.getCreatedBy() != null ? idea.getCreatedBy().getName()
                                                                : null)
                                                .createdAt(idea.getCreatedAt())
                                                .build());
        }

        @Override
        public IdeaResponse getIdeaById(Long id) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException(
                                                "Selected ID not found, please check and try again."));

                return IdeaResponse.builder()
                                .id(idea.getId())
                                .title(idea.getTitle())
                                .description(idea.getDescription())
                                .techStack(idea.getTechStack())
                                .difficulty(idea.getDifficulty())
                                .createdBy(idea.getCreatedBy() != null ? idea.getCreatedBy().getName() : null)
                                .createdAt(idea.getCreatedAt())
                                .build();
        }

        @Override
        public void deleteIdeaById(Long id) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for id: " + id));

                String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                if (!idea.getCreatedBy().getEmail().equals(currentEmail)) {
                        throw new UnauthorizedAccessException("You are not authorized to modify this idea.");
                }

                ideaRepo.delete(idea);
        }

        @Override
        public IdeaResponse updateIdeaById(Long id, IdeaRequest request) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for this Id"));

                String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                if (!idea.getCreatedBy().getEmail().equals(currentEmail)) {
                        throw new UnauthorizedAccessException("You are not authorized to modify this idea.");
                }

                idea.setTitle(request.getTitle());
                idea.setDescription(request.getDescription());
                idea.setDifficulty(request.getDifficulty());
                idea.setTechStack(request.getTechStack());

                Idea updated = ideaRepo.save(idea);

                return IdeaResponse.builder()
                                .id(updated.getId())
                                .title(updated.getTitle())
                                .description(updated.getDescription())
                                .difficulty(updated.getDifficulty())
                                .techStack(updated.getTechStack())
                                .createdBy(updated.getCreatedBy().getName())
                                .createdAt(updated.getCreatedAt())
                                .build();
        }

        @Override
        public IdeaResponse patchIdeaById(Long id, IdeaRequest request) {

                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for id: " + id));

                String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                if (!idea.getCreatedBy().getEmail().equals(currentEmail)) {
                        throw new UnauthorizedAccessException("You are not authorized to modify this idea.");
                }

                if (request.getTitle() != null)
                        idea.setTitle(request.getTitle());
                if (request.getDescription() != null)
                        idea.setDescription(request.getDescription());
                if (request.getDifficulty() != null)
                        idea.setDifficulty(request.getDifficulty());
                if (request.getTechStack() != null)
                        idea.setTechStack(request.getTechStack());

                Idea updated = ideaRepo.save(idea);

                return IdeaResponse.builder()
                                .id(updated.getId())
                                .title(updated.getTitle())
                                .description(updated.getDescription())
                                .difficulty(updated.getDifficulty())
                                .techStack(updated.getTechStack())
                                .createdBy(updated.getCreatedBy().getName())
                                .createdAt(updated.getCreatedAt())
                                .build();
        }

        @Override
        public List<IdeaResponse> getMyIdeas() {

                String email = SecurityContextHolder.getContext().getAuthentication().getName();

                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                return ideaRepo.findByCreatedById(user.getId())
                                .stream()
                                .map(idea -> IdeaResponse.builder()
                                                .id(idea.getId())
                                                .title(idea.getTitle())
                                                .description(idea.getDescription())
                                                .difficulty(idea.getDifficulty())
                                                .techStack(idea.getTechStack())
                                                .createdBy(idea.getCreatedBy().getName())
                                                .createdAt(idea.getCreatedAt())
                                                .build())
                                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                                .toList();
        }
}
