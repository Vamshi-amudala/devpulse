package com.example.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idea.dto.IdeaRequest;
import com.example.idea.dto.IdeaResponse;
import com.example.idea.service.IdeaServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    @Autowired
    public IdeaServiceImpl ideaService;

    @PostMapping("/create")
    public ResponseEntity<IdeaResponse> createIdea(@Valid @RequestBody IdeaRequest request) {

        return new ResponseEntity<>(ideaService.createIdea(request), HttpStatus.CREATED);
    }

}
