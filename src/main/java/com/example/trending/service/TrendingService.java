package com.example.trending.service;

import java.util.List;

import com.example.vote.dto.TrendingIdeaResponse;
import com.example.vote.dto.TrendingResponse;

public interface TrendingService {

    List<TrendingResponse> getTopTrending(int limit);

    List<TrendingIdeaResponse> getTopTrendingIdeas(int limit);
}
