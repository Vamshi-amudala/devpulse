package com.example.vote.service;

public interface VoteService {

    String vote(Long implementationId);

    long getVoteCount(Long implementationId);
}
