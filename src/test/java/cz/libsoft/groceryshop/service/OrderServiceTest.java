package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.request.OrderPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private static long orderId = 0L;

    @Test
    void contextLoads() throws Exception {
        assertThat(orderService).isNotNull();
    }

    @BeforeEach
    public void storeOrder(){
        Order order = new Order();
        order.setTotalPrice(BigDecimal.valueOf(1200L));
        orderId = orderRepository.save(order).getId();
    }

    @AfterEach
    public void deleteOrder(){
        orderRepository.deleteById(orderId);
    }

    @Test
    void whenPricePaid_thenOrderStatusShouldBePaid() throws GroceryShopException {
        List<String> messages = new ArrayList<>();
        OrderPaymentRequest op = OrderPaymentRequest.builder()
                .id(orderId)
                .amountPaid(BigDecimal.valueOf(1200L))
                .build();

        Order paidOrder = orderService.payOrder(op,messages);
        Assertions.assertEquals(paidOrder.getStatus(), OrderStatus.PAID);
    }
}
