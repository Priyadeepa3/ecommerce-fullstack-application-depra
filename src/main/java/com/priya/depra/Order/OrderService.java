package com.priya.depra.Order;


import com.priya.depra.Cart.Cart;
import com.priya.depra.Cart.CartRepository;
import com.priya.depra.Notification.NotificationService;
import com.priya.depra.Product.Product;
import com.priya.depra.Product.ProductRepository;
import com.priya.depra.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Transactional
    public OrderResponsedto placeOrder(User user, String shippingAddress) {

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place order with an empty cart");
        }

        BigDecimal totalAmount = cart.getTotalPrice();

        if (totalAmount == null) {
            totalAmount = cart.getItems().stream()
                    .map(item -> {
                        if (item.getSubtotal() != null) {
                            return item.getSubtotal();
                        }
                        if(item.getPriceAtTime() != null) {
                            return item.getPriceAtTime()
                                    .multiply(BigDecimal.valueOf(item.getQuantity()));
                        }
                        return BigDecimal.ZERO;
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status(orderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .paymentStatus("PENDING")
                .orderDate(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Product " + product.getName() + " is out of stock");
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            return OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .priceAtTime(cartItem.getPriceAtTime())
                    .subtotal(cartItem.getSubtotal())
                    .build();
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        notificationService.createNotification(
                user,
                "Order Placed",
                "Your order has been successfully placed"
        );

        return mapToDto(savedOrder, orderItems);
    }

    public List<OrderResponsedto> getUserOrders(User user) {
        return orderRepository.findByUser(user).stream()
                .map(order -> {
                    List<OrderItem> items = orderItemRepository.findByOrder(order);
                    return mapToDto(order, items);
                })
                .collect(Collectors.toList());
    }

    public OrderResponsedto getOrderById(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to view this order");
        }

        List<OrderItem> items = orderItemRepository.findByOrder(order);
        return mapToDto(order, items);
    }

    private OrderResponsedto mapToDto(Order order, List<OrderItem> items) {
        List<OrderItemdto> itemDtos = items.stream()
                .map(item -> new OrderItemdto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtTime(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponsedto(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getOrderDate(),
                itemDtos,                             // items
                order.getShippingAddress(),           // shippingAddress (correct getter)
                "Cash on Delivery"
        );
    }

    public long countAllOrders() {
        return orderRepository.count();
    }

    public BigDecimal calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderResponsedto> getAllOrdersForAdmin() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    List<OrderItem> items = orderItemRepository.findByOrder(order);
                    return mapToDto(order, items);
                })
                .collect(Collectors.toList());
    }

    public OrderResponsedto updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(orderStatus.valueOf(status.toUpperCase()));
        Order updated = orderRepository.save(order);

        return mapToDto(updated, orderItemRepository.findByOrder(updated));
    }
}