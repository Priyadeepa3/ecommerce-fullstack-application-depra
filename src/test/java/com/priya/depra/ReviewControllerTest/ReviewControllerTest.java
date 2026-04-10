package com.priya.depra.ReviewControllerTest;

import com.priya.depra.Review.ReviewController;
import com.priya.depra.Review.ReviewService;
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

@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    ReviewService reviewService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void createReview() throws Exception {
        mvc.perform(post("/api/reviews")
                        .contentType("application/json")
                        .content("{\"productId\":1,\"rating\":5,\"comment\":\"Nice\"}"))
                .andExpect(status().isOk());
    }
}
