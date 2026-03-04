package com.example.github.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.github.dto.GitHubResponseDTO;
import com.example.implementation.entity.Implementation;
import com.example.implementation.repository.ImplementationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubServiceImpl implements GithubService {

    private final RestTemplate restTemplate;
    private final ImplementationRepository implementationRepository;

    @Value("${github.token}")
    private String githubToken;

    @Override
    public void enrichImplementation(Implementation implementation) {
        try {
            String[] parts = extractOwnerRepo(implementation.getGithubUrl());
            String owner = parts[0];
            String repo = parts[1];

            String url = "https://api.github.com/repos/" + owner + "/" + repo;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/vnd.github.v3+json");
            if (githubToken != null && !githubToken.isEmpty()) {
                headers.setBearerAuth(githubToken);
            }

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<GitHubResponseDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, GitHubResponseDTO.class);

            GitHubResponseDTO body = response.getBody();
            if (body != null) {
                implementation.setRepoName(repo);
                implementation.setStars(body.getStargazersCount());
                implementation.setPrimaryLanguage(body.getLanguage());

                if (body.getPushedAt() != null) {
                    implementation.setLastCommitDate(
                            LocalDateTime.parse(body.getPushedAt(), DateTimeFormatter.ISO_DATE_TIME));
                }
            }

        } catch (IllegalArgumentException e) {
            log.error("Invalid GitHub URL: {}", implementation.getGithubUrl());
            throw new RuntimeException("Invalid GitHub URL format. Must be https://github.com/owner/repo");
        } catch (HttpClientErrorException.NotFound e) {
            log.error("GitHub repository not found: {}", implementation.getGithubUrl());
            throw new RuntimeException(
                    "GitHub repository not found. Make sure the URL is correct and the repository is public.");
        } catch (HttpClientErrorException.Forbidden e) {
            log.error("GitHub repository is private or rate limit hit: {}", implementation.getGithubUrl());
            throw new RuntimeException("GitHub repository is private. DevPulse only supports public repositories.");
        } catch (Exception e) {
            log.error("Error fetching GitHub repository details: {}", e.getMessage());
            // We don't throw here so the implementation can still be saved even if API
            // fails
            // It will just have default stars=0 and null language/date
        }
    }

    private String[] extractOwnerRepo(String githubUrl) {
        if (githubUrl == null || !githubUrl.startsWith("https://github.com/")) {
            throw new IllegalArgumentException("Invalid URL");
        }

        String cleaned = githubUrl.replace("https://github.com/", "");
        cleaned = cleaned.replaceAll("/$", "");
        String[] parts = cleaned.split("/");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid URL");
        }

        return new String[] { parts[0], parts[1] };
    }

    @Scheduled(cron = "0 0 */4 * * *") // Runs every 4 hours
    @Override
    public void syncAllRepositories() {
        log.info("Starting scheduled GitHub sync for all implementations...");
        List<Implementation> implementations = implementationRepository.findAll();

        int successCount = 0;
        for (Implementation impl : implementations) {
            try {
                enrichImplementation(impl);
                implementationRepository.save(impl);
                successCount++;
            } catch (Exception e) {
                log.error("Failed to sync repo for implementation ID {}: {}", impl.getId(), e.getMessage());
            }
        }

        log.info("Completed GitHub sync. Successfully updated {}/{} repositories.", successCount,
                implementations.size());
    }
}
