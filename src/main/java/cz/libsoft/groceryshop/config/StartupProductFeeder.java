package cz.libsoft.groceryshop.config;

import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.model.OrderProduct;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.repository.ProductRepository;
import liquibase.pro.packaged.O;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupProductFeeder {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Bean
    CommandLineRunner runner(){
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Bread");
        product1.setStockQuantity(100);
        product1.setPrice(BigDecimal.valueOf(20L));
        productList.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Milk");
        product2.setStockQuantity(200);
        product2.setPrice(BigDecimal.valueOf(35L));
        productList.add(product2);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Butter");
        product3.setStockQuantity(300);
        product3.setPrice(BigDecimal.valueOf(120L));
        productList.add(product3);
        productRepository.saveAll(productList);

        List<Order> orderList = new ArrayList<>();
        Order order1 = new Order();
        order1.setStatus(OrderStatus.ORDERED);
        order1.setCreatedAt(LocalDateTime.of(2020, 01,01,01,01));
        orderList.add(order1);

        Order order2 = new Order();
        order2.setStatus(OrderStatus.ORDERED);
        order2.setCreatedAt(LocalDateTime.of(2020, 01,01,01,01));
        orderList.add(order2);

        Order order3 = new Order();
        order3.setStatus(OrderStatus.ORDERED);
        order3.setCreatedAt(LocalDateTime.now());
        orderList.add(order3);
        orderRepository.saveAll(orderList);

        return args -> log.info("StartUpDataFeeder end");
    }
}
