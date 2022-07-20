package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.dto.OrderDto;
import cz.libsoft.groceryshop.model.OrderLine;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.model.ProductOrder;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    //create
    @Transactional
    @PostMapping("create")
    ResponseEntity<String> create(@RequestBody(required = false) OrderDto orderDto){

        try {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setCustomerAddress(orderDto.getCustomerAddress());
            productOrder.setStatus(orderDto.getOrderStatus());

            Set<OrderLine> orderLines = new HashSet<>();
            OrderLine orderLine1 = new OrderLine();
            orderLine1.setQuantity(111L);
            Product product1 = productRepository.findById(1L).get();
            orderLine1.setProduct(product1);


            orderLines.add(orderLine1);

            OrderLine orderLine2 = new OrderLine();
            orderLine2.setQuantity(222L);
            Product product2 = productRepository.findById(2L).get();
            orderLine2.setProduct(product2);
            orderLines.add(orderLine2);
            //orderLine.setProductIf(3L);


            productOrder.setOrderLineSet(orderLines);
            orderRepository.save(productOrder);

            return null;
        } catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    //cancel


    //pay
}
