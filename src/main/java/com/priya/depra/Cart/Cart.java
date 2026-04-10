package com.priya.depra.Cart;

import com.priya.depra.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private BigDecimal totalPrice = BigDecimal.valueOf(0.0);

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void setItems(List<CartItem> items) {
        if (this.items == null) {
            this.items = items;
        } else {
            this.items.clear();
            if (items != null) {
                this.items.addAll(items);
            }
        }
    }

    public void addCartItem(CartItem item) {
        items.add(item);
        item.setCart(this); // Maintain the bidirectional relationship
    }

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
