package com.vivatechrnd.task.controller;

import com.vivatechrnd.task.dto.request.LoginRequest;
import com.vivatechrnd.task.dto.request.RegisterRequest;
import com.vivatechrnd.task.dto.response.AuthResponse;
import com.vivatechrnd.task.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Public endpoint to create a new USER or ADMIN account")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        String token = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token));
    }

    @Operation(summary = "Login user", description = "Public endpoint to authenticate and receive JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}