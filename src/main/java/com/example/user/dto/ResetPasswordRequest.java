package com.example.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "OTP is required")
    @Size(min = 6, max = 6, message = "OTP must be exactly 6 digits")
    private String otp;

    @NotBlank(message = "New password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,20}$", message = "Password must contain at least one letter, one number, and one special character")
    private String newPassword;

    
    @NotBlank(message = "Confirm password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,20}$", message = "Password must contain at least one letter, one number, and one special character")
    private String confirmPassword;


}
