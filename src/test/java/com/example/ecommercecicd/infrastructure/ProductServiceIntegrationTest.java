package com.example.ecommercecicd.infrastructure;

import com.example.ecommercecicd.api.ProductFixture;
import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.ProductException;
import com.example.ecommercecicd.busienss.application.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService sut;
    @Test
    @DisplayName("상품이름과 상품가격을 서버에게 등록요청을 하면 상품이 등록된다.")
    void 상품등록(){
        // given
//        AddProductRequest request = ProductFixture.getAddProductRequest(10000);
        Product request = ProductFixture.addProductFixture(1000);
        // when
        sut.addProduct(request);
        // then

    }

    @Test
    @DisplayName("유효하지 않은 가격을 등록하려고 할때 예외를 발생시킨다.")
    void 등록_실패_가격_유효하지않음(){
        // given
//        AddProductRequest request = ProductFixture.getAddProductRequest(-199999);
        Product request = ProductFixture.addProductFixture(-1111000);
        // when
        Exception exception = Assertions.assertThrows(ProductException.class, () -> {
            sut.addProduct(request);
        });
        // then
        Assertions.assertEquals("가격은 0보다 커야합니다.", exception.getMessage());
    }

}
