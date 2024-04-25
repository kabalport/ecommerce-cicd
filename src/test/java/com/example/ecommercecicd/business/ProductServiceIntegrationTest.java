package com.example.ecommercecicd.business;

import com.example.ecommercecicd.api.ProductController;
import com.example.ecommercecicd.busienss.application.ProductService;
import com.example.ecommercecicd.busienss.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ProductServiceIntegrationTest {

    private ProductService productService;


    @Test
    @DisplayName("Test adding a product")
    public void testAddProduct() {
        // Setup
        Product newProduct = new Product(null, "New Product", BigDecimal.valueOf(1500));

        // Execute
        Product savedProduct = productService.addProduct(newProduct);

        // Verify
        assertNotNull(savedProduct.getId());
        assertEquals("New Product", savedProduct.getName());
        assertEquals(0, BigDecimal.valueOf(1500).compareTo(savedProduct.getPrice()));

        // Clean-up is handled by @Transactional
    }
}
