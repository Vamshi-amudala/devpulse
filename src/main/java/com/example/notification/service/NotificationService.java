package com.example.notification.service;

import java.util.List;

import com.example.notification.dto.NotificationResponse;
import com.example.notification.entity.NotificationType;

public interface NotificationService {

    void createNotification(Long userId, NotificationType type, String message, 
            String relatedEntityType, Long relatedEntityId);

    List<NotificationResponse> getRecentNotifications(Long userId, int limit);

    long getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

    void deleteNotificationsByUserId(Long userId);
}
