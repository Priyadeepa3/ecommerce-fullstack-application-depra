package com.priya.depra.Admin.Controller;


import com.priya.depra.Admin.Controller.AdminService;
import com.priya.depra.Product.Productdto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Productdto> createProduct(@RequestBody Productdto dto) {
        return ResponseEntity.ok(adminService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productdto> updateProduct(@PathVariable Long id,
                                                    @RequestBody Productdto dto) {
        return ResponseEntity.ok(adminService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
