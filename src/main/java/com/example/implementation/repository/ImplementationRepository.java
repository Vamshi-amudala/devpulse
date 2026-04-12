package com.example.implementation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.implementation.entity.Implementation;
import com.example.user.entity.User;

@Repository
public interface ImplementationRepository extends JpaRepository<Implementation, Long> {
    List<Implementation> findByIdeaId(Long ideaId);

    List<Implementation> findBySubmittedById(Long userId);

    List<Implementation> findBySubmittedByOrderByCreatedAtDesc(User user);

    Long countBySubmittedBy(User user);
}
