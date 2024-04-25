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
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
        assertEquals(request.getName(),expectedProduct.getName());
        assertEquals(request.getPrice(),expectedProduct.getPrice());
    }

    @Test
    @DisplayName("상품등록-유효하지 않은 가격을 등록하려고 할때 예외를 발생시킨다.")
    void 상품등록_실패_가격_유효하지않음(){
        // given
        Product request = ProductFixture.addProductFixture(-1000);
        // when
        Exception exception = Assertions.assertThrows(ProductException.class, () -> {
            sut.addProduct(request);
        });
        // then
        assertEquals("가격은 0보다 커야합니다.", exception.getMessage());
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
        assertNotNull(getProductResponse);
        assertEquals(givenProduct.getId(),getProductResponse.getId());
    }

    @Test
    @DisplayName("상품 수정 - 상품의 정보를 업데이트할 수 있다.")
    void 상품수정() {
        // given
        Long productId = 9999999L; // Assuming the product has an ID of 1
        Product originalProduct = ProductFixture.existProductOne();
        Product updatedProduct = new Product(productId, "Updated Name", BigDecimal.valueOf(2000));

        when(iproductRepository.read(productId)).thenReturn(Optional.of(originalProduct));

        // when
        Product result = sut.updateProduct(productId, updatedProduct);

        // then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(iproductRepository).save(captor.capture());
        Product capturedProduct = captor.getValue();

        assertNotNull(result);
        assertEquals(capturedProduct.getId(), result.getId());
        assertEquals(capturedProduct.getName(), result.getName());
        assertEquals(capturedProduct.getPrice(), result.getPrice());
    }

    @Test
    @DisplayName("상품 삭제 - 유효한 ID로 상품 삭제 요청을 하면 상품이 삭제된다.")
    void 상품삭제_성공() {
        // given
        Long productId = 9999999L;
        when(iproductRepository.read(productId)).thenReturn(Optional.of(ProductFixture.existProductOne()));
        doNothing().when(iproductRepository).delete(productId);

        // when
        sut.deleteProduct(productId);

        // then
        Mockito.verify(iproductRepository).delete(productId);
    }

    @Test
    @DisplayName("상품 삭제 - 존재하지 않는 ID로 상품 삭제 요청을 하면 예외를 발생시킨다.")
    void 상품삭제_실패() {
        // given
        Long invalidProductId = 100L; // Assuming this product ID does not exist
        when(iproductRepository.read(invalidProductId)).thenReturn(Optional.empty());

        // when
        Exception exception = Assertions.assertThrows(ProductException.class, () -> {
            sut.deleteProduct(invalidProductId);
        });

        // then
        assertEquals("해당 ID의 상품을 찾을 수 없습니다: " + invalidProductId, exception.getMessage());
    }



}
