package com.priya.depra.Product;

import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Component
public class Productmapper {

    public Productdto toDto(Product product) {

        return Productdto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .stockQuantity(product.getStockQuantity())
                .fabric(product.getFabric())
                .color(product.getColor())
                .active(product.isActive())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public Product toEntity(Productdto dto, Category category) {

        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .discountPrice(dto.getDiscountPrice())
                .stockQuantity(dto.getStockQuantity())
                .fabric(dto.getFabric())
                .color(dto.getColor())
                .active(dto.isActive())
                .category(category)
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
