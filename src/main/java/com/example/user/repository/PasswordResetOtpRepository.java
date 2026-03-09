package com.example.user.repository;

import com.example.user.entity.PasswordResetOtp;
import com.example.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {
    Optional<PasswordResetOtp> findByOtp(String otp);

    Optional<PasswordResetOtp> findByUser(User user);

    void deleteByUser(User user);
}
