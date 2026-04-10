package com.priya.depra.Product;

import com.priya.depra.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_category", columnList = "category_id"),
        @Index(name = "idx_price", columnList = "price"),
        @Index(name = "idx_active", columnList = "active")

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal discountPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    private String fabric;

    private String imageUrl;

    private String color;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
