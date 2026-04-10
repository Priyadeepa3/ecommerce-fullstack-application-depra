package com.priya.depra.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponsedto {
    private Long orderId;
    private BigDecimal totalAmount;
    private  String status;
    private LocalDateTime orderDate;
    private List<OrderItemdto> items;
    private String shippingAddress;   // add this
    private String paymentMethod;     // add this


}
