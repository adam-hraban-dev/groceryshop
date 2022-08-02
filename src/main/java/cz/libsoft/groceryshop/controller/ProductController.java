package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.request.ProductRequest;
import cz.libsoft.groceryshop.response.GroceryShopResponse;
import cz.libsoft.groceryshop.response.GroceryShopResponseCode;
import cz.libsoft.groceryshop.response.GroceryShopResponseStatus;
import cz.libsoft.groceryshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PutMapping("create")
    ResponseEntity<GroceryShopResponse> create(@RequestBody(required = false) ProductRequest productRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            productService.createProduct(productRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.OK)
                            .code(GroceryShopResponseCode.PRODUCT_CREATED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error creating product " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.ERROR)
                            .code(GroceryShopResponseCode.PRODUCT_NOT_CREATED)
                            .build());
        }
    }

    @PostMapping("update")
    ResponseEntity<GroceryShopResponse> update(@RequestBody(required = false) ProductRequest productRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            productService.updateProduct(productRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.OK)
                            .code(GroceryShopResponseCode.PRODUCT_UPDATED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error updating product " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.ERROR)
                            .code(GroceryShopResponseCode.PRODUCT_NOT_UPDATED)
                            .build());
        }
    }


    @PostMapping("update-quantity")
    ResponseEntity<GroceryShopResponse> updateQuantity(@RequestBody ProductRequest productRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            productService.updateProductQuantity(productRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.OK)
                            .code(GroceryShopResponseCode.PRODUCT_QUANTITY_UPDATED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error updating product quantity " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.ERROR)
                            .code(GroceryShopResponseCode.PRODUCT_QUANTITY_NOT_UPDATED)
                            .build());
        }
    }

    @DeleteMapping("delete")
    ResponseEntity<GroceryShopResponse> delete(@RequestBody ProductRequest productRequest) {
        List<String> responseMessages = new ArrayList<>();
        try {
            productService.deleteProduct(productRequest, responseMessages);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.OK)
                            .code(GroceryShopResponseCode.PRODUCT_DELETED)
                            .build());

        } catch (GroceryShopException gse) {
            log.error("Error creating order " + gse.getErrorMessages(), gse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GroceryShopResponse.builder()
                            .messages(responseMessages)
                            .status(GroceryShopResponseStatus.ERROR)
                            .code(GroceryShopResponseCode.PRODUCT_NOT_DELETED)
                            .build());
        }
    }
}
