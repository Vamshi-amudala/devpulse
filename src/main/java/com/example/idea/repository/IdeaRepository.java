package com.example.idea.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.idea.dto.IdeaWithCountsProjection;
import com.example.idea.entity.Idea;
import com.example.user.entity.User;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    List<Idea> findByCreatedById(Long userId);

    List<Idea> findByCreatedByOrderByCreatedAtDesc(User user);

    Long countByCreatedBy(User user);

    /**
     * Simple search — no joins.
     * Used when sorting by: createdAt, difficulty
     */
    @Query("""
            SELECT i FROM Idea i
            WHERE LOWER(COALESCE(i.title, '')) LIKE :kwPattern
              AND LOWER(COALESCE(i.techStack, '')) LIKE :tsPattern
              AND (:difficulty IS NULL OR LOWER(i.difficulty) = :difficulty)
            """)
    Page<Idea> searchIdeasSimple(
            @Param("kwPattern") String kwPattern,
            @Param("tsPattern") String tsPattern,
            @Param("difficulty") String difficulty,
            Pageable pageable);

    /**
     * Aggregate search — LEFT JOINs implementations and votes.
     * Used when sorting by: votes, implementations
     */
    @Query(value = """
            SELECT i AS idea,
                   COUNT(DISTINCT impl.id) AS totalImplementations,
                   COUNT(v.id) AS totalVotes
            FROM Idea i
            LEFT JOIN i.implementations impl
            LEFT JOIN impl.votes v
            WHERE LOWER(COALESCE(i.title, '')) LIKE :kwPattern
              AND LOWER(COALESCE(i.techStack, '')) LIKE :tsPattern
              AND (:difficulty IS NULL OR LOWER(i.difficulty) = :difficulty)
            GROUP BY i.id
            """, countQuery = """
            SELECT COUNT(i.id) FROM Idea i
            WHERE LOWER(COALESCE(i.title, '')) LIKE :kwPattern
              AND LOWER(COALESCE(i.techStack, '')) LIKE :tsPattern
              AND (:difficulty IS NULL OR LOWER(i.difficulty) = :difficulty)
            """)
    Page<IdeaWithCountsProjection> searchIdeasAggregate(
            @Param("kwPattern") String kwPattern,
            @Param("tsPattern") String tsPattern,
            @Param("difficulty") String difficulty,
            Pageable pageable);
}
