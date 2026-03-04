package com.example.vote.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.implementation.entity.Implementation;
import com.example.implementation.exception.ImplementationNotFoundException;
import com.example.implementation.repository.ImplementationRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.vote.entity.Vote;
import com.example.vote.exception.SelfVoteException;
import com.example.vote.repository.VoteRepository;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ImplementationRepository impRepo;

    @Override
    public String vote(Long implementationId) {

        // 1. Get current user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 2. Find the implementation
        Implementation implementation = impRepo.findById(implementationId)
                .orElseThrow(() -> new ImplementationNotFoundException(
                        "Implementation not found for id: " + implementationId));

        // 3. Prevent self-voting
        if (implementation.getSubmittedBy().getEmail().equals(email)) {
            throw new SelfVoteException("You cannot vote on your own implementation");
        }

        // 4. Toggle: if already voted → unvote, else → vote
        Optional<Vote> existingVote = voteRepo.findByImplementationIdAndUserId(implementationId, user.getId());

        if (existingVote.isPresent()) {
            voteRepo.delete(existingVote.get());
            return "Unvoted";
        } else {
            Vote vote = Vote.builder()
                    .implementation(implementation)
                    .user(user)
                    .build();
            voteRepo.save(vote);
            return "Voted";
        }
    }

    @Override
    public long getVoteCount(Long implementationId) {
        return voteRepo.countByImplementationId(implementationId);
    }
}
