package com.example.user.service;

import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.RegisterRequest;

public interface UserService {
	
	void register(RegisterRequest dto);
	
	String login(LoginRequestDto dto);

}
