package com.example.ecommercecicd.api;

import com.example.ecommercecicd.api.AddProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class ProductSteps {
    public static ExtractableResponse<Response> 상품등록요청(final AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/products")
                .then()
                .log().all().extract();
    }

    public static AddProductRequest 상품등록요청_생성() {
        final String name = "상품명";
        final BigDecimal price = BigDecimal.valueOf(1000);
        return new AddProductRequest(name, price);
    }

    public static ExtractableResponse<Response> 상품조회요청(final Long productId) {
        return RestAssured.given().log().all()
                .when()
                .get("/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품수정요청(final Long productId, final AddProductRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/products/{productId}", productId)
                .then()
                .log().all().extract();
    }
    public static ExtractableResponse<Response> 상품삭제요청(final Long productId) {
        return RestAssured.given().log().all()
                .when()
                .delete("/products/{productId}", productId)
                .then()
                .log().all().extract();
    }


}