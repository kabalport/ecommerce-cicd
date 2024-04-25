package com.example.ecommercecicd.api;

import com.example.ecommercecicd.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductApiTest extends ApiTest {

    @Test
    void 상품등록() {
        // given
        final var request = ProductFixture.getAddProductRequest(200); // 가격을 200으로 설정한 상품 등록 요청 객체 생성

        // when
        // API 요청
        final var response = ProductSteps.상품등록요청(request);

        // then
        assertEquals(response.statusCode(), HttpStatus.CREATED.value()); // 상태 코드 검증
        // 추가 검증: 응답 바디의 일부 내용 확인
        assertEquals(response.jsonPath().getString("name"), "상품명"); // 응답에서 상품명 검증
    }


    @Test
    void 상품조회() {
        // given
        final var addResponse = ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성()); // 상품을 먼저 등록
        Long productId = addResponse.jsonPath().getLong("id"); // 상품 등록 후 반환된 ID 추출

        // when
        final var response = ProductSteps.상품조회요청(productId); // 상품 조회 요청

        // then
        assertEquals(response.statusCode(), HttpStatus.OK.value()); // 상태 코드 검증
        assertEquals(response.jsonPath().getString("name"), "상품명"); // 응답에서 상품명 검증
    }

//    @Test
//    void 상품수정() {
//        // given
//        final var addResponse = ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
//        Long productId = addResponse.jsonPath().getLong("id");
//        final var updateRequest = new AddProductRequest("수정된 상품명", BigDecimal.valueOf(1500));
//
//        // when
//        final var response = ProductSteps.상품수정요청(productId, updateRequest);
//
//        // then
//        assertEquals(response.statusCode(), HttpStatus.OK.value()); // 상태 코드 검증
//        assertEquals(response.jsonPath().getString("name"), "수정된 상품명"); // 수정된 이름 검증
//    }
//
//    @Test
//    void 상품삭제() {
//        // given
//        final var addResponse = ProductSteps.상품등록요청(ProductSteps.상품등록요청_생성());
//        Long productId = addResponse.jsonPath().getLong("id");
//
//        // when
//        final var response = ProductSteps.상품삭제요청(productId);
//
//        // then
//        assertEquals(response.statusCode(), HttpStatus.NO_CONTENT.value()); // 상태 코드 검증, 204 No Content는 삭제 성공 시 일반적
//    }



}
