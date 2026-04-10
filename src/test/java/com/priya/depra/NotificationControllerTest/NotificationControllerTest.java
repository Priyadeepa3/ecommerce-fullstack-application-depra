package com.priya.depra.NotificationControllerTest;

import com.priya.depra.Notification.NotificationController;
import com.priya.depra.Notification.NotificationService;
import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NotificationControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    NotificationService notificationService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getNotifications() throws Exception {
        mvc.perform(get("/api/notifications"))
                .andExpect(status().isOk());
    }
}
