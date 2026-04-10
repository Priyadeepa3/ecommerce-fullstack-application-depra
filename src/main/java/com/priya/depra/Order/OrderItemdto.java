package com.priya.depra.Order;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemdto {

    private Long productId;

    private String productName;

    private Integer quantity;

    private BigDecimal priceAtTime;

    private BigDecimal subtotal;


}
