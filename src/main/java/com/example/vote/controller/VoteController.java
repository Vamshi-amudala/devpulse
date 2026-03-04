package com.example.vote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vote.service.VoteService;

@RestController
@RequestMapping("/api/implementations")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/{implementationId}/vote")
    public ResponseEntity<String> vote(@PathVariable Long implementationId) {
        String result = voteService.vote(implementationId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{implementationId}/votes")
    public ResponseEntity<Long> getVotes(@PathVariable Long implementationId) {
        return ResponseEntity.ok(voteService.getVoteCount(implementationId));
    }

}
