package com.example.email.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String userName);

    void sendPasswordResetEmail(String toEmail, String otp);

    void sendImplementationNotificationEmail(String toEmail, String ideaTitle, String implementerName, String repoName);
}
