package com.example.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.service.AdminService;
import com.example.idea.dto.IdeaResponse;
import com.example.user.dto.UserResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ─── Users ───────────────────────────────────────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // ─── Ideas ───────────────────────────────────────────────────────────────

    @GetMapping("/ideas")
    public ResponseEntity<List<IdeaResponse>> getAllIdeas() {
        return ResponseEntity.ok(adminService.getAllIdeas());
    }

    @DeleteMapping("/ideas/{id}")
    public ResponseEntity<String> deleteIdea(@PathVariable Long id) {
        adminService.deleteIdea(id);
        return ResponseEntity.ok("Idea deleted successfully.");
    }

    // ─── Implementations ─────────────────────────────────────────────────────

    @DeleteMapping("/implementations/{id}")
    public ResponseEntity<String> deleteImplementation(@PathVariable Long id) {
        adminService.deleteImplementation(id);
        return ResponseEntity.ok("Implementation deleted successfully.");
    }
}
