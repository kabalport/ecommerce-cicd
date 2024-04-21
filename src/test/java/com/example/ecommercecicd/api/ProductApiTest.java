package com.example.ecommercecicd.api;

import com.example.ecommercecicd.ApiTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductApiTest extends ApiTest {

    @Test
    void 상품등록(){
        // given
        final var request = ProductFixture.getAddProductRequest(200);
        // when
        // API 요청
        final var response = ProductFixture.getAddProductResponse(request);
        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value());
    }
}
