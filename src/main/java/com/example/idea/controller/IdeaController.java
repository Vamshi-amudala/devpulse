package com.example.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.idea.dto.IdeaRequest;
import com.example.idea.dto.IdeaResponse;
import com.example.idea.service.IdeaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    @Autowired
    private IdeaService ideaService;

    @PostMapping("/create")
    public ResponseEntity<IdeaResponse> createIdea(@Valid @RequestBody IdeaRequest request) {

        return new ResponseEntity<>(ideaService.createIdea(request), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<IdeaResponse>> getAllIdeas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(ideaService.getAllIdeas(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaResponse> getIdeaByid(@PathVariable Long id) {
        return new ResponseEntity<>(ideaService.getIdeaById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIdeaByID(@PathVariable Long id) {
        ideaService.deleteIdeaById(id);
        return ResponseEntity.ok("Idea deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdeaResponse> updateById(@PathVariable Long id,
            @Valid @RequestBody IdeaRequest request) {

        return new ResponseEntity<>(ideaService.updateIdeaById(id, request), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IdeaResponse> patchById(@PathVariable Long id,
            @RequestBody IdeaRequest request) {
        return ResponseEntity.ok(ideaService.patchIdeaById(id, request));
    }

}
