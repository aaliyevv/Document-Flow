package com.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    @Value("${jwt.secret}") // read secret from yml
    private String Secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(Secret.getBytes());
    }
}
