package com.example.ecommercecicd.order.application;


import com.example.ecommercecicd.member.business.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductOrderTest {

    private MemberRepository memberRepository;

    private ProductOrderService productOrderService;
    private ProductOrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(ProductOrderRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        productOrderService = new ProductOrderService(orderRepository,memberRepository);
    }

    @Test
    @DisplayName("상품주문-사용자가 상품주문요청을 하면 상품주문을 합니다.")
    void 상품주문() {
        // given
        String userId = "userId";
        Long productId = 1L;
        int productQuantity = 3;

        ProductOrderRequest request = new ProductOrderRequest(userId,productId,productQuantity);

        // when
        productOrderService.createOrder(request);

        // then
        Assertions.assertEquals(request.getProductId(),1L);
    }

    private class ProductOrderService {
        private final ProductOrderRepository orderRepository;
        private final MemberRepository memberRepository;

        public ProductOrderService(ProductOrderRepository orderRepository, MemberRepository memberRepository) {

            this.orderRepository = orderRepository;
            this.memberRepository = memberRepository;
        }

        public void createOrder(ProductOrderRequest request) {
            orderRepository.save(request);
        }
    }

    private class ProductOrderRequest {
        private final String userId;
        private final Long productId;
        private final int productQuantity;

        public String getUserId() {
            return userId;
        }

        public Long getProductId() {
            return productId;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public ProductOrderRequest(String userId, Long productId, int productQuantity) {
            this.userId = userId;
            this.productId = productId;
            this.productQuantity = productQuantity;
        }
    }

    private class ProductOrderRepository {
        public void save(ProductOrderRequest request) {

        }
    }
}
