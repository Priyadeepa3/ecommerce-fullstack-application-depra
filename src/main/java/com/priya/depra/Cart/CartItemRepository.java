package com.priya.depra.Cart;

import com.priya.depra.Product.Product;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);



    List<CartItem> findByCart(Cart Cart);


    Optional<CartItem> findByCartAndProduct_Id(Cart cart, Long productId);

    void deleteByCartAndProductId(Cart cart, Long productId);
}
