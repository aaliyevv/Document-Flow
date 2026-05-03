package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // for class
public class PasswordConfig {

    @Bean // for method
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}