package com.priya.depra.Admin.Controller;

import com.priya.depra.Admin.dto.AdminDashBoardDto;
import com.priya.depra.Order.OrderService;
import com.priya.depra.Product.ProductService;
import com.priya.depra.Product.Productdto;
import com.priya.depra.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    // Injecting the beans properly
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    public AdminDashBoardDto getDashboardStats() {
        return AdminDashBoardDto.builder()
                .totalUsers(userService.countAllUsers())
                .totalOrders(orderService.countAllOrders())
                .totalRevenue(orderService.calculateTotalRevenue())
                .lowStockProducts(productService.countLowStockItems(5))
                .build();
    }

    public Productdto createProduct(Productdto dto) {
        return productService.createProduct(dto);
    }

    public Productdto updateProduct(Long id, Productdto dto) {
        return productService.updateProduct(id, dto);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

}