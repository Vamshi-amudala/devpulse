package com.example.idea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.idea.entity.Idea;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

}
