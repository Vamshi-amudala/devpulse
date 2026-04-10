package com.example.idea.dto;

import com.example.idea.entity.Idea;

public interface IdeaWithCountsProjection {
    Idea getIdea();
    Long getTotalImplementations();
    Long getTotalVotes();
}
