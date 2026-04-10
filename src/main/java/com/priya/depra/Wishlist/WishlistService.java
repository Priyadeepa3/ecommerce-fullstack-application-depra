package com.priya.depra.Wishlist;

import com.priya.depra.Product.Product;
import com.priya.depra.Product.ProductRepository;
import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addToWishlist(String email, Long productId) {

        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        if (wishlistRepository.existsByUserAndProduct_Id(user, productId)) {
            throw new RuntimeException("Product already in wishlist");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        wishlistRepository.save(wishlist);
    }

    public List<Wishlistdto> getWishlist(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));


        return wishlistRepository.findByUser(user).stream().map(w ->
                        new Wishlistdto(w.getId(),
                        w.getProduct().getId(),
                        w.getProduct().getName(),
                        w.getProduct().getPrice()
                ))
                .toList();
    }

    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}
