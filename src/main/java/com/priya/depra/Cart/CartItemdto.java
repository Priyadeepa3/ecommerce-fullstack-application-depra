package com.priya.depra.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CartItemdto {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtTime;
    private BigDecimal subtotal;
    private String imageUrl;
}
