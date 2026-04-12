package com.example.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification.service.NotificationService;
import com.example.user.entity.User;
import com.example.user.service.UserService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getRecentNotifications(
            Authentication authentication,
            @RequestParam(defaultValue = "10") int limit) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        var notifications = notificationService.getRecentNotifications(user.getId(), limit);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        long unreadCount = notificationService.getUnreadCount(user.getId());
        return ResponseEntity.ok("{\"unreadCount\": " + unreadCount + "}");
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long notificationId,
            Authentication authentication) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("{\"message\": \"Notification marked as read\"}");
    }
}
