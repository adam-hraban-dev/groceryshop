package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.dto.ProductRequest;
import cz.libsoft.groceryshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PutMapping("create")
    ResponseEntity<String> create(@RequestBody ProductRequest productRequest) {
        List<String> responseMessage = new ArrayList<>();
        try {
            productService.createProduct(productRequest, responseMessage);
            log.info(responseMessage.toString());
            return new ResponseEntity<>(responseMessage.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Endpoint error: " + e.getMessage(), e);
            return new ResponseEntity<>("Endpoint error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("update")
    ResponseEntity<String> update(@RequestBody ProductRequest productRequest) {
        List<String> responseMessage = new ArrayList<>();
        try {
            productService.updateProduct(productRequest, responseMessage);
            log.info(responseMessage.toString());
            return new ResponseEntity<>(responseMessage.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Endpoint error: " + e.getMessage(), e);
            return new ResponseEntity<>("Endpoint error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("update-quantity")
    ResponseEntity<String> updateQuantity(@RequestBody ProductRequest productRequest) {
        List<String> responseMessage = new ArrayList<>();
        try {
            productService.updateProductQuantity(productRequest, responseMessage);
            log.info(responseMessage.toString());
            return new ResponseEntity<>(responseMessage.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Endpoint error: " + e.getMessage(), e);
            return new ResponseEntity<>("Endpoint error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete")
    ResponseEntity<String> delete(@RequestBody ProductRequest productRequest) {
        List<String> responseMessage = new ArrayList<>();
        try {
            productService.deleteProduct(productRequest, responseMessage);
            log.info(responseMessage.toString());
            return new ResponseEntity<>(responseMessage.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Endpoint error: " + e.getMessage(), e);
            return new ResponseEntity<>("Endpoint error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
