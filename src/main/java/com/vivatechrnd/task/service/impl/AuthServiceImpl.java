package com.vivatechrnd.task.service.impl;

import com.vivatechrnd.task.dto.request.LoginRequest;
import com.vivatechrnd.task.dto.request.RegisterRequest;
import com.vivatechrnd.task.entity.User;
import com.vivatechrnd.task.exception.BusinessException;
import com.vivatechrnd.task.exception.ResourceNotFoundException;
import com.vivatechrnd.task.repository.UserRepository;
import com.vivatechrnd.task.security.CustomUserDetails;
import com.vivatechrnd.task.security.JwtService;
import com.vivatechrnd.task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent())
            throw new BusinessException("Username already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        return jwtService.generateToken(new CustomUserDetails(user));
    }

    @Override
    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return jwtService.generateToken(new CustomUserDetails(user));
    }
}
