package com.priya.depra.Auth;


import com.priya.depra.Auth.dto.AuthResponse;
import com.priya.depra.Auth.dto.RefreshRequest;
import com.priya.depra.Security.JwtUtil;
import com.priya.depra.Security.RefreshToken.RefreshToken;
import com.priya.depra.Security.RefreshToken.RefreshTokenRepository;
import com.priya.depra.User.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private  final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;



@Transactional
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );



        String accessToken = jwtUtil.generateToken(request.getEmail());

        String refreshToken = UUID.randomUUID().toString();

        System.out.println("Login method inside AuthService triggered");
        System.out.println("Generated refresh token: " + refreshToken);

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .email(request.getEmail())
                .expiryDate(
                        new Date(System.currentTimeMillis() +
                                7L * 24 * 60 * 60 * 1000)
                )
                .build();

        refreshTokenRepository.deleteByEmail(request.getEmail());
        refreshTokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken);


    }


    public AuthResponse refreshToken(RefreshRequest request) {

        RefreshToken token = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtil.generateToken(token.getEmail());

        return new AuthResponse(newAccessToken, token.getToken());
    }


    @Transactional
    public void logout(RefreshRequest request) {
        refreshTokenRepository.findByToken(request.getRefreshToken())
                .ifPresent(refreshTokenRepository::delete);
    }
}

