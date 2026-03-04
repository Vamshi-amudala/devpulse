package com.example.implementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.implementation.entity.Implementation;

@Repository
public interface ImplementationRepository extends JpaRepository<Implementation, Long> {
    List<Implementation> findByIdeaId(Long ideaId);

    List<Implementation> findBySubmittedById(Long userId);
}
