package com.priya.depra.Product;

import com.priya.depra.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final Productmapper productMapper;
    private final NotificationService notificationService;

    public  List<Productdto> getAllActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(productMapper::toDto).toList();
    }

    public List<Productdto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto).collect(Collectors.toList());
    }

    public void updateStock(Long productId, int stock){
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStockQuantity(stock);
        productRepository.save(product);

        if(stock > 0 && product.getOwner() != null){
            notificationService.createNotification(
                    product.getOwner(),
                    "Product Back in Stock",
                    product.getName() + " is available again"
            );
        }
    }

    public Productdto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    public Productdto createProduct(Productdto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productMapper.toEntity(dto, category);
        return productMapper.toDto(productRepository.save(product));
    }

    public Productdto updateProduct(Long id, Productdto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update all fields
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setDescription(dto.getDescription());

        return productMapper.toDto(productRepository.save(product));
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
                .map(productMapper::toDto).toList();
    }

    public List<Productdto> getByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max).stream()
                .map(productMapper::toDto).toList();
    }


    public Productdto updatePartial(Long id, Map<String, Object> updates) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        updates.forEach((key, value) -> {
            switch (key) {

                case "name":
                    product.setName((String) value);
                    break;

                case "description":
                    product.setDescription((String) value);
                    break;

                case "price":
                    product.setPrice(new BigDecimal(value.toString()));
                    break;

                case "discountPrice":
                    product.setDiscountPrice(new BigDecimal(value.toString()));
                    break;

                case "stockQuantity":
                    product.setStockQuantity((Integer) value);
                    break;

                case "fabric":
                    product.setFabric((String) value);
                    break;

                case "color":
                    product.setColor((String) value);
                    break;

                case "active":
                    product.setActive((Boolean) value);
                    break;

                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });

        Product saved = productRepository.save(product);

        return productMapper.toDto(saved);// your existing mapper
    }


}