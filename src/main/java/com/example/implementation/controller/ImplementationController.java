package com.example.implementation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.implementation.dto.ImplementationRequest;
import com.example.implementation.dto.ImplementationResponse;
import com.example.implementation.service.ImplementationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ImplementationController {

    @Autowired
    private ImplementationService impService;

    @PostMapping("/ideas/{ideaId}/implementations")
    public ResponseEntity<ImplementationResponse> create(
            @PathVariable Long ideaId,
            @Valid @RequestBody ImplementationRequest request) {
        return new ResponseEntity<>(impService.createImplementation(ideaId, request), HttpStatus.CREATED);
    }

    @GetMapping("/ideas/{ideaId}/implementations")
    public ResponseEntity<List<ImplementationResponse>> getByIdea(@PathVariable Long ideaId) {
        return ResponseEntity.ok(impService.getImplementationsByIdea(ideaId));
    }

    @DeleteMapping("/implementations/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        impService.deleteImplementation(id);
        return ResponseEntity.ok("Implementation deleted successfully.");
    }
}
