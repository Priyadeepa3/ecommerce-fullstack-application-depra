package com.priya.depra.Product;

import com.priya.depra.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Productmapper productMapper;
    private final NotificationService notificationService;

    @Value("${app.backend-base-url:https://depra-ecom.onrender.com}")
    private String backendBaseUrl;

    public List<Productdto> getAllActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(product -> {
                    Productdto dto = productMapper.toDto(product);
                    normalizeImageUrl(dto);
                    return dto;
                })
                .toList();
    }

    public List<Productdto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    Productdto dto = productMapper.toDto(product);
                    normalizeImageUrl(dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void updateStock(Long productId, int stock) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Product not found with id: " + productId)
        );
        product.setStockQuantity(stock);
        productRepository.save(product);

        if (stock > 0 && product.getOwner() != null) {
            notificationService.createNotification(
                    product.getOwner(),
                    "Product Back in Stock",
                    product.getName() + " is available again"
            );
        }
    }

    public Productdto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        Productdto dto = productMapper.toDto(product);
        normalizeImageUrl(dto);
        return dto;
    }

    public Productdto createProduct(Productdto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productMapper.toEntity(dto, category);
        Productdto saved = productMapper.toDto(productRepository.save(product));
        normalizeImageUrl(saved);
        return saved;
    }

    public Productdto updateProduct(Long id, Productdto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDescription());
        Productdto saved = productMapper.toDto(productRepository.save(product));
        normalizeImageUrl(saved);
        return saved;
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public long countLowStockItems(int threshold) {
        return productRepository.findAll().stream()
                .filter(p -> p.getStockQuantity() < threshold)
                .count();
    }

    public List<Productdto> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(product -> {
                    Productdto dto = productMapper.toDto(product);
                    normalizeImageUrl(dto);
                    return dto;
                })
                .toList();
    }

    public List<Productdto> getByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max).stream()
                .map(product -> {
                    Productdto dto = productMapper.toDto(product);
                    normalizeImageUrl(dto);
                    return dto;
                })
                .toList();
    }

    public Productdto updatePartial(Long id, Map<String, Object> updates) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name"          -> product.setName((String) value);
                case "description"   -> product.setDescription((String) value);
                case "price"         -> product.setPrice(new BigDecimal(value.toString()));
                case "discountPrice" -> product.setDiscountPrice(new BigDecimal(value.toString()));
                case "stockQuantity" -> product.setStockQuantity((Integer) value);
                case "fabric"        -> product.setFabric((String) value);
                case "color"         -> product.setColor((String) value);
                case "active"        -> product.setActive((Boolean) value);
                case "imageUrl"      -> product.setImageUrl((String) value);
                default -> throw new RuntimeException("Unknown field: " + key);
            }
        });

        Productdto saved = productMapper.toDto(productRepository.save(product));
        normalizeImageUrl(saved);
        return saved;
    }

    private void normalizeImageUrl(Productdto dto) {
        if (dto == null) return;
        String url = dto.getImageUrl();
        if (url == null || url.isBlank()) return;
        url = url.trim();

        if (url.startsWith("http://") || url.startsWith("https://")) return;

        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        dto.setImageUrl(backendBaseUrl + url);
    }
}