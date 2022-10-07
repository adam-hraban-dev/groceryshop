package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.model.OrderHeader;
import cz.libsoft.groceryshop.model.OrderLine;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.OrderRepository;
import cz.libsoft.groceryshop.repository.ProductRepository;
import cz.libsoft.groceryshop.request.OrderLineRequest;
import cz.libsoft.groceryshop.request.OrderPaymentRequest;
import cz.libsoft.groceryshop.request.OrderRequest;
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

    public OrderHeader manageOrder(OrderRequest orderRequest, List<String> messages) throws GroceryShopException {
        if (orderRequest.getCustomerAddress() == null || orderRequest.getOrderLines() == null){
            messages.add("Order not created, customer address is missing or order lines are missing");
            throw new GroceryShopException("Order not created, customer address is missing or order lines are missing", messages);
        }

        OrderHeader orderHeader = new OrderHeader();
        BigDecimal totalPrice = BigDecimal.valueOf(0L);
        orderHeader.setCustomerAddress(orderRequest.getCustomerAddress());
        orderHeader.setStatus(OrderStatus.ORDERED);

        Set<OrderLine> orderLines = new HashSet<>();
        for (OrderLineRequest orderLineRequest : orderRequest.getOrderLines()) {
            OrderLine orderLine = new OrderLine();

            // check product
            Optional<Product> productOptional = productRepository.findById(orderLineRequest.getProductId());
            if (productOptional.isEmpty()) {
                messages.add("Product not found, Id: " + orderLineRequest.getProductId());
                throw new GroceryShopException("Order not created ", messages);
            }

            // check quantity
            int stockQty = productOptional.get().getStockQuantity();
            if (stockQty < orderLineRequest.getQuantity()) {
                messages.add("Not enough product quantity on stock, product Id: " + orderLineRequest.getProductId());
                throw new GroceryShopException("Order not created ", messages);
            }

            // decrease stock quantity
            productOptional.get().setStockQuantity(stockQty - orderLineRequest.getQuantity());
            productRepository.save(productOptional.get());

            orderLine.setProduct(productOptional.get());
            orderLine.setProductQuantity(orderLineRequest.getQuantity());
            orderLines.add(orderLine);

            // add price to total order price
            BigDecimal orderLinePrice = productOptional.get().getPrice().multiply(BigDecimal.valueOf(orderLine.getProductQuantity()));
            totalPrice = totalPrice.add(orderLinePrice);
        }

        // store order
        orderHeader.setOrderLines(orderLines);
        orderHeader.setTotalPrice(totalPrice);
        orderHeader.setCreatedAt(LocalDateTime.now());
        OrderHeader createdOrderHeader = orderRepository.save(orderHeader);
        log.info("Order " + createdOrderHeader.getId() + " created ");
        messages.add("Order " + createdOrderHeader.getId() + " created ");
        return createdOrderHeader;
    }

    public OrderHeader cancelOrder(long orderId, List<String> messages) throws GroceryShopException {
        Optional<OrderHeader> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            messages.add("No order " + orderId + " found, order not cancelled");
            throw new GroceryShopException("Order not found", messages);
        } else {
            orderOptional.get().setStatus(OrderStatus.CANCELLED);

            reallocateStock(orderOptional.get(), messages);

            OrderHeader orderHeaderCancelled = orderRepository.save(orderOptional.get());
            log.info("Order " + orderId + " cancelled");
            messages.add("Order " + orderId + " cancelled");
            return orderHeaderCancelled;
        }
    }

    public OrderHeader payOrder(OrderPaymentRequest orderPaymentRequest, List<String> messages) throws GroceryShopException {
        Optional<OrderHeader> orderOptional = orderRepository.findById(orderPaymentRequest.getId());

        if (orderOptional.isEmpty()) {
            messages.add("No order " + orderPaymentRequest.getId() + " found, order not paid");
            throw new GroceryShopException("Order not found", messages);
        } else if (orderOptional.get().getTotalPrice().compareTo(orderPaymentRequest.getAmountPaid()) != 0) {
            messages.add("Total order price " + orderOptional.get().getTotalPrice() +
                    " not equal to price paid " + orderPaymentRequest.getAmountPaid() +
                    ". Order not paid.");
            throw new GroceryShopException("Order price does not match", messages);
        } else {
            orderOptional.get().setStatus(OrderStatus.PAID);
            OrderHeader paidOrderHeader = orderRepository.save(orderOptional.get());
            messages.add("Order " + orderPaymentRequest.getId() + " paid");
            log.info("Order " + orderPaymentRequest.getId() + " paid");
            return paidOrderHeader;
        }
    }

    public void reallocateStock(OrderHeader orderHeader, List<String> messages) {
        for (OrderLine orderLine : orderHeader.getOrderLines()) {
            int orderQty = orderLine.getProductQuantity();
            int stockQty = orderLine.getProduct().getStockQuantity();
            orderLine.getProduct().setStockQuantity(stockQty + orderQty);
            productRepository.save(orderLine.getProduct());
            messages.add("Product " + orderLine.getProduct().getName() + " stock quantity updated, actual stock: " + (stockQty + orderQty));
            log.info("Product " + orderLine.getProduct().getName() + " stock quantity updated, actual stock: " + (stockQty + orderQty));

            orderLine.setProductQuantity(0);
        }
    }
}
