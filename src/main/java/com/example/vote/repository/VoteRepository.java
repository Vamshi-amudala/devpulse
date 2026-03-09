package com.example.vote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.vote.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByImplementationIdAndUserId(Long implementationId, Long userId);

    long countByImplementationId(Long implementationId);

    @Query("SELECT v.implementation, COUNT(v) FROM Vote v GROUP BY v.implementation ORDER BY COUNT(v) DESC")
    List<Object[]> findTopImplementations(Pageable pageable);

    void deleteByUserId(Long id);
}
