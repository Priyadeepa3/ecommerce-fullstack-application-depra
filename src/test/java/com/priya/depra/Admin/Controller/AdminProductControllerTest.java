package com.priya.depra.Admin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.depra.Product.Productdto;
import com.priya.depra.Security.CustomUserDetailsService;
import com.priya.depra.Security.JwtFilter;
import com.priya.depra.Security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.priya.depra.Admin.Controller.AdminService;

import java.math.BigDecimal;

// CLEANED STATIC IMPORTS - Remove all jdk.jfr imports
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private  AdminService adminService; // Calls your existing logic

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_returnsCreatedProduct() throws Exception {
        Productdto in = new Productdto();
        in.setName("Kanchipuram Saree");
        in.setPrice(new BigDecimal("5000.00")); // FIXED: No double here

        Productdto out = new Productdto();
        out.setId(1L);
        out.setName("Kanchipuram Saree");
        out.setPrice(new BigDecimal("5000.00"));

        // FIXED: adminService now has this method
        when(adminService.createProduct(any(Productdto.class))).thenReturn(out);

        // FIXED: Correct MockMvcRequestBuilders.post import
        mvc.perform(post("/api/admin/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kanchipuram Saree"));
    }
}