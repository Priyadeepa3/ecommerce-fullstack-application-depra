package com.priya.depra.OrderControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.depra.Order.OrderController;
import com.priya.depra.Order.OrderResponsedto;
import com.priya.depra.Order.OrderService;
import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtFilter jwtFilter;

    @Test
    @WithMockUser
    void getMyOrders_returnsList() throws Exception {
        OrderResponsedto dto = new OrderResponsedto(1L, java.math.BigDecimal.valueOf(1000), "PENDING", java.time.LocalDateTime.now(), List.of());
        when(orderService.getUserOrders(org.mockito.ArgumentMatchers.any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/orders/my-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
}

