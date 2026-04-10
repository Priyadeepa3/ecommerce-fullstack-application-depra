package com.priya.depra.Shipping;


import com.priya.depra.Order.Order;
import com.priya.depra.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRespository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentResponsedto createShipment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Shipment shipment = Shipment.builder()
                .order(order)
                .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(ShipmentStatus.PENDING)
                .shippingDate(LocalDate.now())
                .build();

        Shipment saved = shipmentRepository.save(shipment);
        return mapToResponse(saved);
    }

    public ShipmentResponsedto getShipmentByOrder(Long orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    private ShipmentResponsedto mapToResponse(Shipment s) {
        return ShipmentResponsedto.builder()
                .id(s.getId())
                .trackingNumber(s.getTrackingNumber())
                .status(s.getStatus())
                // Ensure s.getOrder() is not null before calling getId()
                .orderId(s.getOrder() != null ? s.getOrder().getId() : null)
                // Mapping address fields
                .destinationStreet(s.getShipmentAddress() != null ? s.getShipmentAddress().getStreet() : "N/A")
                .destinationCity(s.getShipmentAddress() != null ? s.getShipmentAddress().getCity() : "N/A")
                .shippingDate(s.getShippingDate())
                .deliveryDate(s.getDeliveryDate())
                .build();
    }
}