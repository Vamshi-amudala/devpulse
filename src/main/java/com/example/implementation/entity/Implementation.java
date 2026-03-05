package com.example.implementation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Collate;

import com.example.idea.entity.Idea;
import com.example.user.entity.User;
import com.example.vote.entity.Vote;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "implementations")
@Builder
public class Implementation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String githubUrl;

    private String repoName;

    @Builder.Default
    private Integer stars = 0;

    private String primaryLanguage;

    private LocalDateTime lastCommitDate;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;

    @OneToMany(mappedBy = "implementation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Vote> votes = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String approachDescription;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
