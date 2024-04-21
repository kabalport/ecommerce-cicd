package com.example.ecommercecicd.busienss;

import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.repository.IProductRepository;
import com.example.ecommercecicd.infrastructure.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class ProductRepositoryImpl implements IProductRepository {

    final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }
    @Override
    public void save(Product product) {
        productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> read(Long id) {
        Optional<Product> product = productJpaRepository.findById(id);
        return product;
    }

}
