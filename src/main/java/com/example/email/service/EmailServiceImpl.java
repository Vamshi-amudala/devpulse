package com.example.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to DevPulse!");

            String htmlContent = "<h2>Welcome to DevPulse, " + userName + "!</h2>"
                    + "<p>We are thrilled to have you join our developer showcase platform.</p>"
                    + "<p>Start posting your project ideas and submitting implementations for others.</p>"
                    + "<br>"
                    + "<p>Best regards,<br>The DevPulse Team</p>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Welcome email sent successfully to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send welcome email to {}", toEmail, e);
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("DevPulse - Password Reset OTP");

            String htmlContent = "<h2>Password Reset</h2>"
                    + "<p>You requested a password reset. Here is your 6-digit OTP code:</p>"
                    + "<h1 style='color: #4CAF50; letter-spacing: 5px; font-size: 36px; padding: 10px; background-color: #f4f4f4; display: inline-block;'>"
                    + otp + "</h1>"
                    + "<p>This code will expire in 15 minutes.</p>"
                    + "<p>If you did not request this, please ignore this email.</p>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Password reset OTP email sent successfully to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send password reset email to {}", toEmail, e);
        }
    }

    @Override
    @Async
    public void sendImplementationNotificationEmail(String toEmail, String ideaTitle, String implementerName,
            String repoName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("New Implementation Submitted for your Idea!");

            String htmlContent = "<h2>Good news!</h2>"
                    + "<p>Someone has submitted an implementation for your idea: <strong>" + ideaTitle + "</strong></p>"
                    + "<p><strong>" + implementerName + "</strong> just linked their GitHub repository: <strong>"
                    + repoName + "</strong></p>"
                    + "<p>Check it out on DevPulse!</p>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Implementation notification email sent successfully to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send implementation notification email to {}", toEmail, e);
        }
    }
}
