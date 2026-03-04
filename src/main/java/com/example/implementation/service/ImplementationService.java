package com.example.implementation.service;

import java.util.List;

import com.example.implementation.dto.ImplementationRequest;
import com.example.implementation.dto.ImplementationResponse;

public interface ImplementationService {

    List<ImplementationResponse> getImplementationsByIdea(Long ideaId);

    void deleteImplementation(Long id);

    ImplementationResponse createImplementation(Long ideaId, ImplementationRequest request);

}
