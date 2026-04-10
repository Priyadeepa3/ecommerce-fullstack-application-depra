package com.priya.depra.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponsedto {

    private Long cartId;
    private BigDecimal totalAmount;
    private List<CartItemdto> items;
}
