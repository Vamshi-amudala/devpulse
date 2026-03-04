package com.example.github.service;

import com.example.implementation.entity.Implementation;

public interface GithubService {

    void enrichImplementation(Implementation implementation);

    void syncAllRepositories();
}
