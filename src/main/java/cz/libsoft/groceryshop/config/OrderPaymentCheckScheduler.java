package cz.libsoft.groceryshop.config;


import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentCheckScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Scheduled(fixedRate = 20000, initialDelay = 1000)
    public void checkDelayedOrders() {
        log.info("Scheduled job checkDelayedOrders started.");
        updateOrderStatus(OrderStatus.ORDERED);
    }

    private void updateOrderStatus(OrderStatus orderStatus) {
        List<Order> orderList = orderRepository.findByStatus(orderStatus);
        List<Order> ordersToBeUpdated = new ArrayList<>();
        for (Order order : orderList) {
            long minutesDuration = Duration.between(order.getCreatedAt(), LocalDateTime.now()).toMinutes();
            if (minutesDuration >= 30) {
                order.setStatus(OrderStatus.CANCELLED);
                orderService.reallocateStock(order, new ArrayList<>());
                log.info("Order " + order.getId() + " cancelled, order not paid in time. Products reallocated.");
                ordersToBeUpdated.add(order);
            }
        }
        orderRepository.saveAll(ordersToBeUpdated);
    }
}
