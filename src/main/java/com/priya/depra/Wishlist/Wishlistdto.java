package com.priya.depra.Wishlist;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wishlistdto {

    private Long wishlistId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;



}
