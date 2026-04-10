package com.priya.depra.Shipping;

import com.priya.depra.Order.Order;
import com.priya.depra.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "Shipping")
@Entity
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @ManyToOne
    private Order order;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private ShipmentAddress shipmentAddress;

    private LocalDate shippingDate;

    private LocalDate deliveryDate;



}
