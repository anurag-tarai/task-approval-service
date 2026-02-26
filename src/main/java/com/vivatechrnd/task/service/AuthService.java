package com.vivatechrnd.task.service;

import com.vivatechrnd.task.dto.request.LoginRequest;
import com.vivatechrnd.task.dto.request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
}