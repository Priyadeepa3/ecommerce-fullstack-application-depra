package com.priya.depra.Order;

import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping("/place")
    public ResponseEntity<OrderResponsedto> placeOrder( @Valid
            @RequestBody PlaceOrderRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        System.out.println("Order List" + email);

        OrderResponsedto response = orderService.placeOrder(user, request.getShippingAddress());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponsedto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponsedto>> getMyOrders(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderService.getUserOrders(user));


    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponsedto> getOrder(
            Authentication authentication,
            @PathVariable Long orderId) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderService.getOrderById(user, orderId));
    }
}