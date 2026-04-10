package com.example.config;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * Simple in-memory sliding window rate limiter.
 * Uses a per-client key (e.g. "login:IP") to track request counts.
 *
 * Limits: MAX_REQUESTS per WINDOW_MS milliseconds.
 * Note: For multi-instance deployments, use Redis-backed Bucket4j instead.
 */
@Component
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;          // 5 attempts
    private static final long WINDOW_MS = 60_000;       // per 1 minute

    private final ConcurrentHashMap<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();

    /**
     * Returns true if the request is allowed, false if rate limit exceeded.
     *
     * @param key unique identifier, e.g. "login:192.168.1.1"
     */
    public synchronized boolean isAllowed(String key) {
        long now = System.currentTimeMillis();

        Deque<Long> timestamps = requestMap.computeIfAbsent(key, k -> new ArrayDeque<>());

        // Remove timestamps outside the sliding window
        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > WINDOW_MS) {
            timestamps.pollFirst();
        }

        if (timestamps.size() >= MAX_REQUESTS) {
            return false;
        }

        timestamps.addLast(now);
        return true;
    }
}
