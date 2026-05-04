package com.controller;

import com.dto.AuthResponse;
import com.dto.LoginRequest;
import com.dto.RegisterRequest;
import com.entity.User;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public String register(
            @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return "User Registered Successfully";
    }

    public AuthResponse login(@RequestBody LoginRequest loginRequest){

        User user = userService.
    }

}
