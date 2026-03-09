package com.example.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.user.dto.ForgotPasswordRequest;
import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.ResetPasswordRequest;
import com.example.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest dto) {
        userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDto dto) {
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
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest dto) {
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
