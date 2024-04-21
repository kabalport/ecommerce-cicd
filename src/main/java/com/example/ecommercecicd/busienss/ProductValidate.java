package com.example.ecommercecicd.busienss;

import com.example.ecommercecicd.busienss.domain.Product;

import java.math.BigDecimal;

public class ProductValidate {
    public static void validate(Product request) {
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductException("가격은 0보다 커야합니다.");
        }
    }
}
