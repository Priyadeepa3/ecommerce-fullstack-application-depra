package com.priya.depra.Order;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderRequest {

    @NotBlank(message = "Address is required")
    private String shippingAddress;
}
