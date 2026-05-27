package com.documentflow.exception;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 → not found, 401 → bad login
    //403 → forbidden action, 500 → real server error

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(
            BadCredentialsException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<String> handleUnauthorized(
            UnauthorizedActionException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}