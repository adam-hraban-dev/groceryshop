package cz.libsoft.groceryshop.config;

import cz.libsoft.groceryshop.model.OrderHeader;
import cz.libsoft.groceryshop.model.OrderLine;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    CommandLineRunner runner() {
        List<Product> productList = new ArrayList<>();

        // products
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

        // orders
        List<OrderHeader> orderHeaderList = new ArrayList<>();

        OrderLine orderLine1 = new OrderLine();
        orderLine1.setProduct(product2);
        orderLine1.setProductQuantity(1);

        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setStatus(OrderStatus.ORDERED);
        orderHeader1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 1, 1));
        orderHeader1.setOrderLines(Set.of(orderLine1));
        orderHeader1.setCustomerAddress("Address 1");
        orderHeaderList.add(orderHeader1);
        orderLine1.setOrderHeader(orderHeader1);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setStatus(OrderStatus.ORDERED);
        orderHeader2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 1, 1));
        orderHeaderList.add(orderHeader2);

        OrderHeader orderHeader3 = new OrderHeader();
        orderHeader3.setStatus(OrderStatus.ORDERED);
        orderHeader3.setCreatedAt(LocalDateTime.now());
        orderHeaderList.add(orderHeader3);
        orderRepository.saveAll(orderHeaderList);

        return args -> log.info("StartUp DataFeeder end");
    }
}
