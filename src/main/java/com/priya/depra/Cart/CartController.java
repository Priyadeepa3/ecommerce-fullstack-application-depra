package com.priya.depra.Cart;

import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // Helper method to get User entity from Authentication
    private User getUserFromAuth(Authentication auth) {
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Add product to cart (already correct)
    @PostMapping("/add")
    public CartResponsedto addToCart(Authentication authentication,
                                     @RequestBody AddToCartRequest request) {
        User user = getUserFromAuth(authentication);
        return cartService.addToCart(user, request.getProductId(), request.getQuantity());
    }

    // Get current user cart – now uses Authentication
    @GetMapping
    public ResponseEntity<CartResponsedto> getCart(Authentication authentication) {
        User user = getUserFromAuth(authentication);
        return ResponseEntity.ok(cartService.getCart(user));
    }

    // Update quantity
    @PutMapping("/update")
    public CartResponsedto updateQuantity(Authentication authentication,
                                          @RequestBody AddToCartRequest request) {
        User user = getUserFromAuth(authentication);
        return cartService.updateQuantity(user, request.getProductId(), request.getQuantity());
    }

    // Remove item
    @DeleteMapping("/remove/{productId}")
    public CartResponsedto removeItem(Authentication authentication,
                                      @PathVariable Long productId) {
        User user = getUserFromAuth(authentication);
        return cartService.removeItem(user, productId);
    }

    // Clear cart
    @DeleteMapping("/clear")
    public void clearCart(Authentication authentication) {
        User user = getUserFromAuth(authentication);
        cartService.clearCart(user);
    }
}