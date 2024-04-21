package com.example.ecommercecicd.api;

import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.application.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
