package com.example.ecommercecicd.api;

import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.application.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> addProduct(@RequestBody final AddProductRequest request){
        Product product = new Product(request.getName(),request.getPrice());
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable final Long productId) {
        final Product product = productService.read(productId);

        final GetProductResponse response = new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody AddProductRequest request) {
        Product updatedProduct = new Product(request.getName(), request.getPrice());
        updatedProduct.setId(productId);
        productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


}
