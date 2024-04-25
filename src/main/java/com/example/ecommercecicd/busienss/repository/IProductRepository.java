package com.example.ecommercecicd.busienss.repository;


import com.example.ecommercecicd.busienss.domain.Product;

import java.util.Optional;

public interface IProductRepository {
    Product save(Product product);

    Optional<Product> read(Long id);

    void delete(Long productId);
}
