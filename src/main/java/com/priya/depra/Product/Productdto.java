package com.priya.depra.Product;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Productdto {

    private Long id;
    private String name;
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stockQuantity;
    private String fabric;
    private String color;
    private boolean active;
    private Long categoryId;
    private String imageUrl;
    private String categoryName;


}
