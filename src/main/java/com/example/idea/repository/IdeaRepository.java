package com.example.idea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.idea.entity.Idea;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    List<Idea> findByCreatedById(Long userId);

}
