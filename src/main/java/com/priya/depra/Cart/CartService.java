package com.priya.depra.Cart;

import com.priya.depra.Product.ProductRepository;
import com.priya.depra.User.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.priya.depra.Product.Product;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponsedto addToCart(User user, Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.isActive()) {
            throw new RuntimeException("Product is inactive");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        BigDecimal price = product.getDiscountPrice() != null
                ? product.getDiscountPrice()
                : product.getPrice();

        cartItem.setPriceAtTime(price);
        cartItem.setSubtotal(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        cartItemRepository.save(cartItem);

        recalculateAndReturn(cart);

        return mapToDto(cart);
    }


// --- MISSING METHODS CALLED BY CONTROLLER ---

    @Transactional
    public CartResponsedto getCart(User user) {
        // Find the cart. If it doesn't exist, create it immediately.
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalPrice(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });
        return mapToDto(cart);
    }
    @Transactional
    public  void clearCart(User user){
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    // Helper to refresh data and map to DTO
    @Transactional
    private CartResponsedto recalculateAndReturn(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);


        BigDecimal total = items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);



        cart.setTotalPrice(total);
        cartRepository.save(cart);
        return mapToDto(cart);
    }


    private CartResponsedto mapToDto(Cart cart) {

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemdto> itemDtos = items.stream()
                .map(item -> new CartItemdto(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtTime(),
                        item.getSubtotal(),
                        item.getProduct().getImageUrl()
                ))
                .toList();

        return new CartResponsedto(
                cart.getId(),
                cart.getTotalPrice(),
                itemDtos
        );
    }
    @Transactional
    public CartResponsedto updateQuantity(User user, Long productId, Integer quantity) {
        if (quantity <= 0) return removeItem(user, productId);

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = (CartItem) cartItemRepository.findByCartAndProduct_Id(cart, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        item.setQuantity(quantity);
        item.setSubtotal(item.getPriceAtTime().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(item);

        return recalculateAndReturn(cart);
    }

    @Transactional
    public CartResponsedto removeItem(User user, Long productId) {
        Cart cart = cartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Now cart.getItems() is already loaded (no extra query)
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        // Also delete from CartItemRepository
        cartItemRepository.deleteByCartAndProductId(cart, productId);

        return recalculateAndReturn(cart);
    }

}