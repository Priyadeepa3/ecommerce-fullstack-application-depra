package com.priya.depra.Email;

import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Set in Render environment variables:
     *   KEY:   app.frontend-url
     *   VALUE: https://depra-ecom.vercel.app   (your Vercel production domain)
     *
     * Fallback keeps behaviour safe if the env var is missing.
     */
    @Value("${app.frontend-url:https://depra-ecom.onrender.com}")
    private String frontendUrl;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        // Always return 200 regardless of whether the email exists.
        // Throwing an error for unknown emails leaks which addresses are registered.
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiry(LocalDateTime.now().plusHours(1));
            userRepository.save(user);

            // Points to the frontend (Vercel), not the backend (Render)
            String link = frontendUrl + "/resetPassword.html?token=" + token;
            emailService.sendResetEmail(email, link);
        });

        return ResponseEntity.ok("If this email is registered, a reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String token = req.get("token");
        String newPassword = req.get("newPassword");

        if (token == null || token.isBlank() || newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("Token and new password are required");
        }

        if (newPassword.length() < 8) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters");
        }

        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            user.setResetToken(null);
            user.setTokenExpiry(null);
            userRepository.save(user);
            return ResponseEntity.badRequest().body("Reset token has expired. Please request a new one.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }
}