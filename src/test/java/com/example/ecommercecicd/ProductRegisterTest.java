package com.example.ecommercecicd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

class ProductRegisterTest {
    private ProductRegister productRegister;
    private ProductJpaRepository productJpaRepository;

    private IProductRepository iProductRepository;

    @BeforeEach
    void setUp() {
        productJpaRepository = mock(ProductJpaRepository.class);
        iProductRepository = new ProductRepositoryImpl(productJpaRepository);
        productRegister = new ProductRegister(iProductRepository);
    }

    @Test
    @DisplayName("상품이름과 상품가격을 서버에 등록요청을 하면 상품이 등록된다.")
    void create_product_success() {
        // given
        AddProductRequest addProductRequest = ProductFixture.getAddProductRequest();

        // when
        productRegister.addProduct(addProductRequest);

        // then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productJpaRepository,Mockito.times(1)).save(captor.capture());
        Product expectedProduct = captor.getValue();
        Assertions.assertEquals(addProductRequest.name(),expectedProduct.getName());
        Assertions.assertEquals(addProductRequest.price(),expectedProduct.getPrice());
    }

    public static class Product {
        private Long id;
        private final String name;
        private final int price;

        public String getName() {
            return name;
        }
        public int getPrice() {
            return price;
        }
        public Product(String name, int price) {

            this.name = name;
            this.price = price;
        }

        public void assignId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    public record AddProductRequest(String name, int price) {}

    public interface IProductRepository {

        void save(Product product);
    }
    public static class ProductRegister {
        private final IProductRepository iProductRepository;

        public ProductRegister(IProductRepository iProductRepository) {
            this.iProductRepository = iProductRepository;
        }

        public void addProduct(AddProductRequest addProductRequest) {
            Product product = new Product(addProductRequest.name(), addProductRequest.price());
            iProductRepository.save(product);
        }
    }

public static class ProductRepositoryImpl implements IProductRepository {
        private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }
    @Override
        public void save(Product product) {
            productJpaRepository.save(product);
        }
    }
    private static class ProductJpaRepository {
        private Long sequence = 0L;
        private Map<Long, Product> persistence = new HashMap<>();

        public void save(Product product) {
             product.assignId(++sequence);
             persistence.put(product.getId(), product);
        }
    }
    public class ProductFixture {
            public static AddProductRequest getAddProductRequest() {
                final String givenProductName = "name";
                final int givenProductPrice = 10000;
                final AddProductRequest givenAddProductRequest = new AddProductRequest(givenProductName, givenProductPrice);
                return givenAddProductRequest;
            }
    }
}
