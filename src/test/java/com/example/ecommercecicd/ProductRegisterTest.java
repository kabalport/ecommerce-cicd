package com.example.ecommercecicd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

class ProductRegisterTest {
    private ProductRegisterService sut;
    private IProductRepository iproductRepository;

    @BeforeEach
    void setUp() {
        iproductRepository = mock(IProductRepository.class);
        sut = new ProductRegisterService(iproductRepository);
    }

    @Test
    @DisplayName("상품이름과 상품가격을 서버에게 등록요청을 하면 상품이 등록된다.")
    void 상품등록(){
        // given
        AddProductRequest request = ProductFixture.getAddProductRequest(10000);

        // when
        sut.execute(request);

        // then
        //상품이름과 상품가격을 서버에게 등록요청을 하면 상품이 등록된다.
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(iproductRepository,Mockito.times(1)).save(captor.capture());
        Product expectedProduct = captor.getValue();
        Assertions.assertEquals(request.getName(),expectedProduct.getName());
        Assertions.assertEquals(request.getPrice(),expectedProduct.getPrice());
    }

    public class ProductFixture {
        public static AddProductRequest getAddProductRequest(int val) {
            String name = "productName";
            BigDecimal price = BigDecimal.valueOf(val);
            AddProductRequest request = new AddProductRequest(name, price);
            return request;
        }
    }



    @Test
    @DisplayName("유효하지 않은 가격을 등록하려고 할때 예외를 발생시킨다.")
    void 등록_실패_가격_유효하지않음(){
        AddProductRequest request = ProductFixture.getAddProductRequest(-199999);
        Exception exception = Assertions.assertThrows(ProductException.class, () -> {
            sut.execute(request);
        });

        Assertions.assertEquals("가격은 0보다 커야합니다.", exception.getMessage());

    }

    public static class AddProductRequest {
        private final String name;
        private final BigDecimal price;

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public AddProductRequest(String productName, BigDecimal price) {
            this.name = productName;
            this.price = price;
        }
    }

    private class Product {
        private String name;
        private BigDecimal price;

        public BigDecimal getPrice() {
            return price;
        }

        public Product(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }
    }

    private class ProductRegisterService {
        private IProductRepository iproductRepository;

        public ProductRegisterService(IProductRepository iproductRepository) {
            this.iproductRepository = iproductRepository;
        }

        public void execute(AddProductRequest request) {
            ProductValidate.validate(request);

            Product product = new Product(request.getName(),request.getPrice());
            iproductRepository.save(product);
        }
    }


    public static class ProductValidate {
        static void validate(AddProductRequest request) {
            if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ProductException("가격은 0보다 커야합니다.");
            }
        }
    }

    public interface IProductRepository {
        void save(Product product);
    }

    public static class ProductException extends RuntimeException {
        public ProductException(String message) {
            super(message);
        }
    }
}
