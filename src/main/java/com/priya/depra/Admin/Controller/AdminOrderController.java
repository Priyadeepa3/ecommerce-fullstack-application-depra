package com.priya.depra.Admin.Controller;

import com.priya.depra.Order.OrderResponsedto;
import com.priya.depra.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponsedto>> getAllOrders() {
        // You can add a method in OrderService to find all orders
        // For now, we use the dashboard count logic as a reference
        return ResponseEntity.ok(orderService.getAllOrdersForAdmin());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponsedto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
