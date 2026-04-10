package com.priya.depra.CartControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.depra.Cart.CartController;
import com.priya.depra.Cart.CartService;
import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;



    @Test
    void addToCart_returnsCart() throws Exception {
        // build request JSON as your controller expects
        mockMvc.perform(post("/api/cart/add")
                        .contentType("application/json")
                        .content("{\"productId\":1,\"quantity\":1}"))
                .andExpect(status().isOk()); // change expected status to match your controller
    }
}
