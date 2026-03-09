package com.example.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.config.JwtUtil;
import com.example.email.service.EmailService;
import com.example.user.dto.*;
import com.example.user.entity.*;
import com.example.user.exception.*;
import com.example.user.repository.PasswordResetOtpRepository;
import com.example.user.repository.UserRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordResetOtpRepository otpRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            PasswordResetOtpRepository otpRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.otpRepo = otpRepo;
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

        // Send welcome email asynchronously
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());

        log.info("User registration successful for: {}", dto.getEmail());
    }

    @Override
    public String login(LoginRequestDto dto) {

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        // Delete any existing OTPs for this user
        otpRepo.findByUser(user).ifPresent(otpRepo::delete);

        // Generate a 6-digit OTP
        SecureRandom random = new SecureRandom();
        int otpValue = 100000 + random.nextInt(900000); // 100000 to 999999
        String otpString = String.valueOf(otpValue);

        PasswordResetOtp otp = PasswordResetOtp.builder()
                .otp(otpString)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        otpRepo.save(otp);

        // Send the email with the OTP
        emailService.sendPasswordResetEmail(user.getEmail(), otpString);
        log.info("Password reset OTP email sent for: {}", user.getEmail());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetOtp resetOtp = otpRepo.findByOtp(request.getOtp())
                .orElseThrow(() -> new InvalidOtpException("Invalid or missing OTP code"));

        if (resetOtp.isExpired()) {
            otpRepo.delete(resetOtp);
            throw new InvalidOtpException("OTP code has expired");
        }

        User user = resetOtp.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);

        // Delete the OTP so it cannot be reused
        otpRepo.delete(resetOtp);

        log.info("Password reset successful for user: {}", user.getEmail());
    }
}