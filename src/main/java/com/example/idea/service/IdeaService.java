package com.example.idea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.idea.dto.IdeaRequest;
import com.example.idea.dto.IdeaResponse;

public interface IdeaService {

    IdeaResponse createIdea(IdeaRequest dto);

    Page<IdeaResponse> getAllIdeas(Pageable pageable);

    IdeaResponse getIdeaById(Long id);
}
