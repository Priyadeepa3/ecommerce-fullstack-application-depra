package com.priya.depra.Order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
