package com.example.ecommercecicd.busienss.application;

import com.example.ecommercecicd.busienss.ProductException;
import com.example.ecommercecicd.busienss.repository.IProductRepository;
import com.example.ecommercecicd.busienss.domain.Product;
import com.example.ecommercecicd.busienss.ProductValidate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private IProductRepository iproductRepository;

    public ProductService(IProductRepository iproductRepository) {
        this.iproductRepository = iproductRepository;
    }

    /**
     * 상품추가
     * @param addProductInfo
     */
    public Product addProduct(Product addProductInfo) {
        // 요청한 값을 유효성 검사합니다.
        ProductValidate.validate(addProductInfo);

        // 추가할 상품을 만듭니다.
        Product product = new Product(addProductInfo.getName(), addProductInfo.getPrice());

        // 추가할 상품을 데이터베이스에 저장합니다.
        Product savedProduct = iproductRepository.save(product);

        // 저장된 상품을 반환합니다.
        return savedProduct;
    }


    /**
     * 상품읽기
     *
     * @param productId
     * @return
     */
    public Product read(Long productId) {
        // 데이터베이스에서 요청한 상품아이디를 조회후 결과를 가져옵니다. 조회후 상품이 없으면 예외를 던집니다.
        Product result = iproductRepository.read(productId).orElseThrow(() -> new ProductException("해당 ID의 상품을 찾을 수 없습니다: " + productId));
        // 결과반환
        return result;
    }


    public Product updateProduct(Long productId, Product updatedProductInfo) {
        Product existingProduct = iproductRepository.read(productId)
                .orElseThrow(() -> new ProductException("해당 ID의 상품을 찾을 수 없습니다: " + productId));

        existingProduct.setName(updatedProductInfo.getName());
        existingProduct.setPrice(updatedProductInfo.getPrice());

        ProductValidate.validate(existingProduct);

        iproductRepository.save(existingProduct);

        // Step 5: Return the updated product
        return existingProduct;
    }

    public void deleteProduct(Long productId) {
        Product product = iproductRepository.read(productId)
                .orElseThrow(() -> new ProductException("해당 ID의 상품을 찾을 수 없습니다: " + productId));
        iproductRepository.delete(productId);
    }
}
