package com.documentflow.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    //DocumentService doesn't know email, only workflow like enterprise.

    private final JavaMailSender javaMailSender;
    private Authentication authentication;

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )

    public void sendEmail(String subject, String text) {

        String to = "elcanabdullazada@gmail.com";

        System.out.println("Sending email to " + to);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    @Recover
    public void recover(Exception e) {

        System.out.println("Email sending failed: " + e.getMessage());
    }
}
