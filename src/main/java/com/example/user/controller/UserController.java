package com.example.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.config.RateLimiterService;
import com.example.user.dto.ForgotPasswordRequest;
import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.ResetPasswordRequest;
import com.example.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RateLimiterService rateLimiter;

    public UserController(UserService userService, RateLimiterService rateLimiter) {
        this.userService = userService;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest dto) {
        userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {
        String clientKey = "login:" + request.getRemoteAddr();
        if (!rateLimiter.isAllowed(clientKey)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("error", "Too many login attempts. Please wait 1 minute and try again."));
        }
        String token = userService.login(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok(Map.of("email", email, "role", role));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest dto,
            HttpServletRequest request) {
        String clientKey = "forgot:" + request.getRemoteAddr();
        if (!rateLimiter.isAllowed(clientKey)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("error", "Too many requests. Please wait 1 minute and try again."));
        }
        userService.forgotPassword(dto);
        return ResponseEntity
                .ok("If that email address is in our database, we will send you an email to reset your password.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest dto) {
        userService.resetPassword(dto);
        return ResponseEntity.ok("Password has been reset successfully. You can now login with your new password.");
    }
}
