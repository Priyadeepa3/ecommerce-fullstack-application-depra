package com.priya.depra.OrderControllerTest;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.priya.depra.Order.OrderController;
import com.priya.depra.Order.OrderResponsedto;
import com.priya.depra.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@RequiredArgsConstructor
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void getMyOrders_returnsList() throws Exception {

        OrderResponsedto dto = OrderResponsedto.builder()
                .orderId(1L)
                .totalAmount(BigDecimal.valueOf(1000))
                .status("PENDING")
                .orderDate(LocalDateTime.now())
                .items(List.of())
                .shippingAddress("Chennai, Tamil Nadu")
                .paymentMethod("COD")
                .build();

        when(orderService.getUserOrders(null))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/orders/my-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
}


