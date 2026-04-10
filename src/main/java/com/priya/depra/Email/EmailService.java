package com.priya.depra.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("DEPRA Password Reset");
        message.setText("Click this link to reset your password:\n" + resetLink + "\n\nThis link expires in 1 hour.");

        System.out.println("Reset link: " + resetLink);

        mailSender.send(message);
    }
}