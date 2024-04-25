package com.example.ecommercecicd.api;

public record GetProductResponse(
        long id,
        String name,
        java.math.BigDecimal price
) {
    public GetProductResponse {

    }
}
