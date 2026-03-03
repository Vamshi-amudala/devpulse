package com.example.idea.service;

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
import com.example.idea.repository.IdeaRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class IdeaServiceImpl implements IdeaService {

        @Autowired
        public IdeaRepository ideaRepo;

        @Autowired
        public UserRepository userRepo;

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
                                .techStack(request.getTechStacks())
                                .createdBy(user)
                                .build();

                Idea saved = ideaRepo.save(idea);

                IdeaResponse ideaResponse = IdeaResponse.builder()
                                .id(saved.getId())
                                .title(saved.getTitle())
                                .description(saved.getDescription())
                                .difficulty(saved.getDifficulty())
                                .techStack(saved.getTechStack())
                                .createdBy(user.getEmail())
                                .createdAt(saved.getCreatedAt())
                                .build();

                return ideaResponse;
        }

        @Override
        public Page<IdeaResponse> getAllIdeas(Pageable pageable) {

                Page<Idea> page = ideaRepo.findAll(pageable);

                Page<IdeaResponse> ideasPage = page.map(idea -> IdeaResponse.builder()

                                .id(idea.getId())
                                .title(idea.getTitle())
                                .description(idea.getDescription())
                                .difficulty(idea.getDifficulty())
                                .techStack(idea.getTechStack())
                                .createdBy(idea.getCreatedBy() != null ? idea.getCreatedBy().getEmail() : null)
                                .createdAt(idea.getCreatedAt())
                                .build());

                return ideasPage;
        }

        @Override
        public IdeaResponse getIdeaById(Long id) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Selected ID not found, please check and try again."));

                IdeaResponse ideasRes = IdeaResponse.builder()
                                .id(idea.getId())
                                .title(idea.getTitle())
                                .description(idea.getDescription())
                                .techStack(idea.getTechStack())
                                .difficulty(idea.getDifficulty())
                                .createdBy(idea.getCreatedBy().getEmail())
                                .createdAt(idea.getCreatedAt())
                                .build();

                return ideasRes;
        }

        @Override
        public void deleteIdeaById(Long id) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for id: " + id));
                ideaRepo.delete(idea);
        }

        @Override
        public IdeaResponse updateIdeaById(Long id, IdeaRequest request) {
                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for this Id"));

                idea.setTitle(request.getTitle());
                idea.setDescription(request.getDescription());
                idea.setDifficulty(request.getDifficulty());
                idea.setTechStack(request.getTechStacks());

                Idea updated = ideaRepo.save(idea);

                return IdeaResponse.builder()
                                .id(updated.getId())
                                .title(updated.getTitle())
                                .description(updated.getDescription())
                                .difficulty(updated.getDifficulty())
                                .techStack(updated.getTechStack())
                                .createdBy(updated.getCreatedBy().getEmail())
                                .createdAt(updated.getCreatedAt())
                                .build();

        }

        @Override
        public IdeaResponse patchIdeaById(Long id, IdeaRequest request) {

                Idea idea = ideaRepo.findById(id)
                                .orElseThrow(() -> new IdeaNotFoundByIDException("Idea not found for id: " + id));

                if (request.getTitle() != null)
                        idea.setTitle(request.getTitle());
                if (request.getDescription() != null)
                        idea.setDescription(request.getDescription());
                if (request.getDifficulty() != null)
                        idea.setDifficulty(request.getDifficulty());
                if (request.getTechStacks() != null)
                        idea.setTechStack(request.getTechStacks());

                Idea updated = ideaRepo.save(idea);

                return IdeaResponse.builder()
                                .id(updated.getId())
                                .title(updated.getTitle())
                                .description(updated.getDescription())
                                .difficulty(updated.getDifficulty())
                                .techStack(updated.getTechStack())
                                .createdBy(updated.getCreatedBy().getEmail())
                                .createdAt(updated.getCreatedAt())
                                .build();
        }

}
