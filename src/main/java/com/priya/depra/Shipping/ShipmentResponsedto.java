package com.priya.depra.Shipping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponsedto {

    private Long id;
    private String trackingNumber;
    private ShipmentStatus status;
    private Long orderId;

    // FIX: Add address fields to the response
    private String destinationStreet;
    private String destinationCity;

    private LocalDate shippingDate;
    private LocalDate deliveryDate;
}
