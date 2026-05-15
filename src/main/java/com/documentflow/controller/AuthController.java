package com.documentflow.controller;

import com.documentflow.dto.AuthResponse;
import com.documentflow.dto.LoginRequest;
import com.documentflow.dto.RegisterRequest;
import com.documentflow.security.JwtService;
import com.documentflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return "Registration was Successfull";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(loginRequest.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
