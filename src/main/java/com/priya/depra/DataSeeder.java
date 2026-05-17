package com.priya.depra;

import com.priya.depra.Product.Category;
import com.priya.depra.Product.CategoryRepository;
import com.priya.depra.Product.Product;
import com.priya.depra.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Seeds the database with initial product and category data
 * if the product table is empty. Safe to run on every startup
 * — does nothing if products already exist.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            System.out.println("DataSeeder: Products already exist, skipping seed.");
            return;
        }

        System.out.println("DataSeeder: Seeding categories and products...");

        // Create categories
        Category sarees    = saveCategory("Sarees");
        Category kurtis    = saveCategory("Kurtis");
        Category blouses   = saveCategory("Blouses");
        Category fabrics   = saveCategory("Fabrics");

        // Seed products — image paths match files in src/main/resources/static/images/
        List<Product> products = List.of(
            product("Blue Silk Saree",           "Elegant blue silk saree with golden border",         2499, 3199, "blue_Silk.jpeg",                    "Blue",         "Silk",   sarees,  50),
            product("Pink Peach Saree",           "Soft pink peach saree perfect for celebrations",    1999, 2599, "Pink_Peach.jpeg",                    "Pink",         "Silk",   sarees,  40),
            product("Coffee Silk Saree",          "Deep coffee tone silk saree for quiet elegance",    2199, 2799, "Coffee_Silk.jpeg",                   "Coffee",       "Silk",   sarees,  35),
            product("Red Silk Saree",             "Vibrant red silk saree for festive occasions",      2399, 2999, "red_silk.jpeg",                      "Red",          "Silk",   sarees,  30),
            product("Green Kanjivaram Saree",     "Traditional green kanjivaram with rich zari",       3499, 4299, "green_kanjivaram.jpeg",              "Green",        "Silk",   sarees,  20),
            product("Lavender Tissue Saree",      "Delicate lavender tissue saree, light and breezy",  1799, 2299, "lavender_tissue.jpeg",               "Lavender",     "Tissue", sarees,  45),
            product("Maroon Banarasi Saree",      "Rich maroon banarasi with intricate weave",         3199, 3999, "maroon_banarasi.jpeg",               "Maroon",       "Silk",   sarees,  25),
            product("Mustard Chanderi Saree",     "Breezy mustard chanderi for festive and casual",    1599, 2099, "mustard_chanderi.jpeg",              "Mustard Yellow","Chanderi",sarees,60),
            product("Aqua Blue Georgette Saree",  "Flowy aqua blue georgette for parties",             1899, 2499, "Aqua_Blue_Georgette.jpeg",           "Aqua Blue",    "Georgette",sarees,55),
            product("White Cotton Saree",         "Crisp white cotton saree for daily elegance",       1299, 1699, "white_cotton.jpeg",                  "White",        "Cotton", sarees,  70),

            product("Baby Pink Cotton Kurti",     "Comfortable baby pink cotton kurti for daily wear", 1625, 1999, "Baby_Pink_Cotton_Daily_Kurti.jpeg",  "Baby Pink",    "Cotton", kurtis,  80),
            product("Daily Wear Cotton Kurti",    "Everyday cotton kurti, soft and breathable",        1399, 1799, "daily_wear_cotton_kurti.jpeg",       "White",        "Cotton", kurtis,  90),

            product("Black Red Designer Blouse",  "Stunning black and red designer blouse",            1924, 2500, "Black_Red_Designer_Blouse.jpeg",     "Black",        "Blouse", blouses, 40),
            product("Blue Golden Embroidery Blouse","Blue blouse with golden embroidery work",          486,  899,  "Blue_Golden_Embroidery_Blouse.jpeg", "Blue",         "Fabric", blouses, 60),
            product("Cotton Blouse Piece",        "Plain cotton blouse piece, easy to stitch",          350,  599,  "cotton_Blouse_Piece.jpeg",           "White",        "Cotton", blouses, 100),
            product("Red Designer Blouse",        "Elegant red designer blouse for silk sarees",        899,  1299, "red_Designer_Blouse.jpeg",           "Red",          "Blouse", blouses, 50),

            product("Blue Silk Fabric",           "Premium blue silk fabric, 1 metre",                  799,  999,  "blue_Silk.jpeg",                    "Blue",         "Silk",   fabrics, 200),
            product("Red Velvet Blouse Fabric",   "Luxurious red velvet fabric for blouses",            650,  899,  "red_Velvet_Blouse.jpeg",             "Red",          "Velvet", fabrics, 150)
        );

        productRepository.saveAll(products);
        System.out.println("DataSeeder: Seeded " + products.size() + " products successfully.");
    }

    private Category saveCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName(name);
                    return categoryRepository.save(c);
                });
    }

    private Product product(String name, String description,
                            double price, double mrp,
                            String imageFile, String color,
                            String fabric, Category category,
                            int stock) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(BigDecimal.valueOf(price));
        p.setDiscountPrice(BigDecimal.valueOf(price));
        p.setStockQuantity(stock);
        p.setImageUrl("images/" + imageFile);   // relative — normalizeImageUrl() prepends backend URL
        p.setColor(color);
        p.setFabric(fabric);
        p.setCategory(category);
        p.setActive(true);
        return p;
    }
}
