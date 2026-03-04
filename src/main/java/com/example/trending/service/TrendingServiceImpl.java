package com.example.trending.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.implementation.entity.Implementation;
import com.example.vote.dto.TrendingResponse;
import com.example.vote.repository.VoteRepository;

@Service
public class TrendingServiceImpl implements TrendingService {

    @Autowired
    private VoteRepository voteRepo;

    @Cacheable(value = "trending", key = "#limit")
    @Override
    public List<TrendingResponse> getTopTrending(int limit) {
        if (limit <= 0)
            limit = 10;
        List<Object[]> rows = voteRepo.findTopImplementations(PageRequest.of(0, limit));

        List<TrendingResponse> result = new java.util.ArrayList<>();

        for (Object[] row : rows) {
            Implementation impl = (Implementation) row[0];
            Long voteCount = (Long) row[1];

            result.add(TrendingResponse.builder()
                    .implementationId(impl.getId())
                    .githubUrl(impl.getGithubUrl())
                    .repoName(impl.getRepoName())
                    .stars(impl.getStars())
                    .primaryLanguage(impl.getPrimaryLanguage())
                    .submittedBy(impl.getSubmittedBy().getEmail())
                    .ideaTitle(impl.getIdea().getTitle())
                    .voteCount(voteCount)
                    .build());
        }

        return result;
    }
}
