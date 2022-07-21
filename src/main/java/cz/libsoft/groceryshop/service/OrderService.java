package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.dto.OrderLineDto;
import cz.libsoft.groceryshop.dto.OrderPaymentRequest;
import cz.libsoft.groceryshop.dto.OrderRequest;
import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.model.OrderProduct;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.repository.ProductRepository;
import liquibase.pro.packaged.P;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void manageOrder(OrderRequest orderRequest, List<String> messages) {
        Order order = new Order();
        BigDecimal totalPrice = BigDecimal.valueOf(0L);
        order.setCustomerAddress(orderRequest.getCustomerAddress());
        order.setStatus(OrderStatus.ORDERED);

        Set<OrderProduct> orderProducts = new HashSet<>();
        for (OrderLineDto orderLineDto : orderRequest.getOrderLines()) {
            OrderProduct orderProduct = new OrderProduct();

            // check product
            Optional<Product> productOptional = productRepository.findById(orderLineDto.getProductId());
            if (productOptional.isEmpty()) {
                messages.add("Product not found, Id: " + orderLineDto.getProductId());
                continue;
            }

            // check quantity
            int stockQty = productOptional.get().getStockQuantity();
            if (stockQty < orderLineDto.getQuantity()) {
                messages.add("Not enough product quantity on stock, product Id: " + orderLineDto.getProductId());
                continue;
            }

            // decrease stock quantity
            productOptional.get().setStockQuantity(stockQty - orderLineDto.getQuantity());
            productRepository.save(productOptional.get());

            orderProduct.setProduct(productOptional.get());
            orderProduct.setProductQuantity(orderLineDto.getQuantity());
            orderProducts.add(orderProduct);

            // add price to total order price
            BigDecimal orderLinePrice = productOptional.get().getPrice().multiply(BigDecimal.valueOf(orderProduct.getProductQuantity()));
            totalPrice = totalPrice.add(orderLinePrice);
        }

        // store order
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(totalPrice);
        order.setCreatedAt(LocalDateTime.now());
        Order createdOrder = orderRepository.save(order);
        messages.add("Order " + createdOrder.getId() + " created ");
    }

    public void cancelOrder(long orderId, List<String> messages) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            messages.add("No order " + orderId + " found, order not cancelled");
        } else {
            orderOptional.get().setStatus(OrderStatus.CANCELLED);

            reallocateStock(orderOptional.get(), messages);

            orderRepository.save(orderOptional.get());
            messages.add("Order " + orderId + " cancelled");
        }
    }

    public void payOrder(OrderPaymentRequest orderPaymentRequest, List<String> messages) {
        Optional<Order> orderOptional = orderRepository.findById(orderPaymentRequest.getId());

        if (orderOptional.isEmpty()) {
            messages.add("No order " + orderPaymentRequest.getId() + " found, order not paid");
        } else if (orderOptional.get().getTotalPrice().equals(orderPaymentRequest.getAmountPaid())) {
            messages.add("Total order price " + orderOptional.get().getTotalPrice() +
                    " not equal to price paid " + orderPaymentRequest.getAmountPaid() +
                    ". Order not paid.");
        } else {
            orderOptional.get().setStatus(OrderStatus.PAID);
            orderRepository.save(orderOptional.get());
            messages.add("Order " + orderPaymentRequest.getId() + " paid");
        }
    }

    public void reallocateStock(Order order, List<String> messages) {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            int orderQty = orderProduct.getProductQuantity();
            int stockQty = orderProduct.getProduct().getStockQuantity();
            orderProduct.getProduct().setStockQuantity(stockQty + orderQty);
            productRepository.save(orderProduct.getProduct());
            messages.add("Product" + orderProduct.getProduct().getName() + " stock quantity updated, actual stock: " + (stockQty + orderQty));

            orderProduct.setProductQuantity(0);
        }
    }
}
