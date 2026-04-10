package com.priya.depra.AuthenticationControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.depra.Auth.AuthService;
import com.priya.depra.Auth.AuthenticationController;
import com.priya.depra.Auth.dto.AuthResponse;
import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import com.priya.depra.User.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;


    @Test
    void login_returnsToken() throws Exception {
        LoginRequest in = new LoginRequest();
        in.setEmail("babu31@gmail.com");
        in.setPassword("123456");




        AuthResponse out = new AuthResponse("access-token", "refresh-token");

        when(authService.login(org.mockito.ArgumentMatchers.any()))
                .thenReturn(out);


        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

    }


}
