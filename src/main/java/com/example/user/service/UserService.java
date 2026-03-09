package com.example.user.service;

import com.example.user.dto.ForgotPasswordRequest;
import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.RegisterRequest;
import com.example.user.dto.ResetPasswordRequest;

public interface UserService {

	void register(RegisterRequest dto);

	String login(LoginRequestDto dto);

	void forgotPassword(ForgotPasswordRequest request);

	void resetPassword(ResetPasswordRequest request);

}
