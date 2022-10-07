package cz.libsoft.groceryshop.config;


import cz.libsoft.groceryshop.model.OrderHeader;
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
import java.util.Optional;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class OrderPaymentCheckScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Scheduled(fixedRate = 20000, initialDelay = 10000)
    public void checkDelayedOrders() {
        log.info("Scheduled job checkDelayedOrders started.");
        updateOrderStatus(OrderStatus.ORDERED);
    }

    private void updateOrderStatus(OrderStatus orderStatus) {
        List<OrderHeader> orderHeaderList =  orderRepository.findByStatus(orderStatus);
        List<OrderHeader> ordersToBeUpdated = new ArrayList<>();

        for (OrderHeader orderHeader : orderHeaderList) {
            long minutesDuration = Duration.between(orderHeader.getCreatedAt(), LocalDateTime.now()).toMinutes();
            if (minutesDuration >= 30) {
                orderHeader.setStatus(OrderStatus.CANCELLED);
                orderService.reallocateStock(orderHeader, new ArrayList<>());
                log.info("Order " + orderHeader.getId() + " cancelled, order not paid in time. Products reallocated.");
                ordersToBeUpdated.add(orderHeader);
            }
        }
        orderRepository.saveAll(ordersToBeUpdated);
    }
}
