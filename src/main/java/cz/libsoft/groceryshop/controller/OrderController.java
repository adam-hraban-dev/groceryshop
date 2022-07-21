package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.dto.OrderLineDto;
import cz.libsoft.groceryshop.dto.OrderPaymentRequest;
import cz.libsoft.groceryshop.dto.OrderRequest;
import cz.libsoft.groceryshop.model.OrderProduct;
import cz.libsoft.groceryshop.model.OrderStatus;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.service.OrderService;
import cz.libsoft.groceryshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    //create
    @PostMapping("create")
    ResponseEntity<String> create(@RequestBody OrderRequest orderRequest){
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.manageOrder(orderRequest, responseMessages);
            log.info(responseMessages.toString());
            return new ResponseEntity<>(responseMessages.toString(), HttpStatus.ACCEPTED);

        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //cancel
    @PostMapping("cancel")
    ResponseEntity<String> cancel(@RequestBody OrderRequest orderRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.cancelOrder(orderRequest.getId(), responseMessages);
            log.info(responseMessages.toString());
            return new ResponseEntity<>(responseMessages.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //pay
    @PostMapping("pay")
    ResponseEntity<String> pay(@RequestBody OrderPaymentRequest orderPaymentRequest){
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.payOrder(orderPaymentRequest, responseMessages);
            log.info(responseMessages.toString());
            return new ResponseEntity<>(responseMessages.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
