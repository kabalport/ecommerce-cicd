package com.example.ecommercecicd.business;

import com.example.ecommercecicd.api.ProductFixture;
import com.example.ecommercecicd.busienss.repository.IProductRepository;
import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.ProductException;
import com.example.ecommercecicd.busienss.application.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceUnitTest {
    private IProductRepository iproductRepository;
    private ProductService sut;


    @BeforeEach
    void setUp() {
        iproductRepository = mock(IProductRepository.class);
        sut = new ProductService(iproductRepository);
    }
    @Test
    @DisplayName("상품등록-상품이름과 상품가격을 서버에게 등록요청을 하면 상품이 등록된다.")
    void 상품등록(){
        // given
        Product request = ProductFixture.addProductFixture(1000);
        // when
        sut.addProduct(request);
        // then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(iproductRepository,Mockito.times(1)).save(captor.capture());
        Product expectedProduct = captor.getValue();
        Assertions.assertEquals(request.getName(),expectedProduct.getName());
        Assertions.assertEquals(request.getPrice(),expectedProduct.getPrice());
    }

    @Test
    @DisplayName("상품등록-유효하지 않은 가격을 등록하려고 할때 예외를 발생시킨다.")
    void 등록_실패_가격_유효하지않음(){
        // given
        Product request = ProductFixture.addProductFixture(-1000);
        // when
        Exception exception = Assertions.assertThrows(ProductException.class, () -> {
            sut.addProduct(request);
        });
        // then
        Assertions.assertEquals("가격은 0보다 커야합니다.", exception.getMessage());
    }

    @DisplayName("상품을 조회합니다.")
    @Test
    void 상품조회() {
        //given
        Product givenProduct = ProductFixture.existProductOne();
        when(iproductRepository.read(givenProduct.getId())).thenReturn(Optional.of(givenProduct));
        //when
        Product getProductResponse = sut.read(givenProduct.getId());
        //then
        // 상품의 응답을 검증
        Assertions.assertNotNull(getProductResponse);
        Assertions.assertEquals(givenProduct.getId(),getProductResponse.getId());
    }

}
