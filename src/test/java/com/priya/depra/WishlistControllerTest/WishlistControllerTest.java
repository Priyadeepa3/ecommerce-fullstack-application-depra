package com.priya.depra.WishlistControllerTest;


import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import com.priya.depra.Wishlist.WishlistController;
import com.priya.depra.Wishlist.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(WishlistController.class)
    @AutoConfigureMockMvc(addFilters = false)
  public class WishlistControllerTest {
        @Autowired
        MockMvc mvc;
        @MockBean
        WishlistService wishlistService;

    // --- SECURITY MOCKS START ---
    @MockBean
    private JwtUtil jwtUtil; // Satisfies constructor parameter 0

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

        @Test
        void getWishlist() throws Exception {
            mvc.perform(get("/api/wishlist"))
                    .andExpect(status().isOk());
        }
    }
