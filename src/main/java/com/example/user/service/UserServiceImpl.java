package com.example.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtUtil;
import com.example.user.dto.*;
import com.example.user.entity.*;
import com.example.user.exception.*;
import com.example.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepo,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest dto) {

        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();

        userRepo.save(user);

        log.info("User registration successful for: {}", dto.getEmail());
    }

    @Override
    public String login(LoginRequestDto dto) {

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}