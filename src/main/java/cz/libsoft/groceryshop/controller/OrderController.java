package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.request.OrderPaymentRequest;
import cz.libsoft.groceryshop.request.OrderRequest;
import cz.libsoft.groceryshop.response.GroceryShopResponse;
import cz.libsoft.groceryshop.response.GroceryShopResponseCode;
import cz.libsoft.groceryshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    ResponseEntity create(@RequestBody OrderRequest orderRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.manageOrder(orderRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_CREATED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error creating order " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_NOT_CREATED)
                            .build());
        }
    }

    @PostMapping("cancel")
    ResponseEntity<GroceryShopResponse> cancel(@RequestBody OrderRequest orderRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.cancelOrder(orderRequest.getId(), responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_CANCELLED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error cancelling order " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_NOT_CANCELLED)
                            .build());
        }
    }

    @PostMapping("pay")
    ResponseEntity<GroceryShopResponse> pay(@RequestBody OrderPaymentRequest orderPaymentRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            orderService.payOrder(orderPaymentRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_PAID)
                            .build());
        } catch (GroceryShopException gse) {
            log.error("Error during order payment " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .groceryShopResponseCode(GroceryShopResponseCode.ORDER_NOT_PAID)
                            .build());
        }
    }
}
