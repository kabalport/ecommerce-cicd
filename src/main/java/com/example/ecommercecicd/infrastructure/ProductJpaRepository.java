package com.example.ecommercecicd.infrastructure;

import com.example.ecommercecicd.busienss.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product,Long> {

}
