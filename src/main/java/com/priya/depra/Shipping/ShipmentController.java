package com.priya.depra.Shipping;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<ShipmentResponsedto> initiateShipment(@PathVariable Long orderId) {
        return ResponseEntity.ok(shipmentService.createShipment(orderId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentResponsedto> getShipment(@PathVariable Long orderId) {
        return ResponseEntity.ok(shipmentService.getShipmentByOrder(orderId));
    }
}
