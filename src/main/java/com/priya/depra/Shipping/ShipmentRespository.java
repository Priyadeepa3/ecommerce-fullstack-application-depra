package com.priya.depra.Shipping;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ShipmentRespository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByOrderId(Long orderId);
}
