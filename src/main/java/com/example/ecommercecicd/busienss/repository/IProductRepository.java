package com.example.ecommercecicd.busienss.repository;


import com.example.ecommercecicd.busienss.domain.Product;

import java.util.Optional;

public interface IProductRepository {
    void save(Product product);

    Optional<Product> read(Long id);
}
