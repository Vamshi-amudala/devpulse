package com.example.vote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vote.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByImplementationIdAndUserId(Long implementationId, Long userId);

    long countByImplementationId(Long implementationId);
}
