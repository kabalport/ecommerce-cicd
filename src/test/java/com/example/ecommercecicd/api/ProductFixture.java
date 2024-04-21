package com.example.ecommercecicd.api;

import com.example.ecommercecicd.busienss.domain.Product;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class ProductFixture {


    public static AddProductRequest getAddProductRequest(int val) {
        String name = "productName";
        BigDecimal price = BigDecimal.valueOf(val);
        AddProductRequest request = new AddProductRequest(name, price);
        return request;
    }

    public static ExtractableResponse<Response> getAddProductResponse(AddProductRequest request) {
            return RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/products")
                    .then()
                    .log().all().extract();

    }

    public static Product addProductFixture(int val){
        String name = "addProduct";
        BigDecimal price = BigDecimal.valueOf(val);
        return new Product(name,price);
    }

    public static Product existProductOne(){
        Long givenId = 9999999L;
        String name = "existProduct";
        BigDecimal price = BigDecimal.valueOf(1000);
        return new Product(givenId,name,price);
    };

}
