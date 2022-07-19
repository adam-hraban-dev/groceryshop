package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import cz.libsoft.groceryshop.response.ProductResponse;
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
import java.util.Optional;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    @PutMapping("create")
    ResponseEntity<String> create(@RequestBody(required = false) ProductResponse productResponse) {
        if (productResponse == null || productResponse.getName() == null || productResponse.getPrice() == null) {
            log.info("Missing parameters, no product created");
            return new ResponseEntity<>("Missing parameters, no product created", HttpStatus.EXPECTATION_FAILED);
        } else {
            Product newProduct = productRepository.save(buildProduct(productResponse.getName(), productResponse.getPrice()));
            log.info("Product created: " + newProduct);
            return new ResponseEntity<>("Product created: " + newProduct, HttpStatus.CREATED);
        }
    }

    @PostMapping("update")
    ResponseEntity<String> update(@RequestBody(required = false) ProductResponse productResponse) {
        if (productResponse == null || productResponse.getId() == null || productResponse.getName() == null || productResponse.getPrice() == null) {
            log.info("Missing parameters, no product updated");
            return new ResponseEntity<>("Missing parameters, no product updated", HttpStatus.EXPECTATION_FAILED);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productResponse.getId());
            if (optionalProduct.isEmpty()) {
                log.info("No product with id " + productResponse.getId() + " found");
                return new ResponseEntity<>("No product with id " + productResponse.getId() + " found", HttpStatus.EXPECTATION_FAILED);
            } else {
                optionalProduct.get().setName(productResponse.getName());
                optionalProduct.get().setPrice(BigDecimal.valueOf(Double.valueOf(productResponse.getPrice())));
                productRepository.save(optionalProduct.get());
                log.info("Product updated: " + optionalProduct.get());
                return new ResponseEntity<>("Product updated: " + optionalProduct.get(), HttpStatus.ACCEPTED);
            }
        }
    }

    @PostMapping("update-quantity")
    ResponseEntity<String> updateQuantity(@RequestBody(required = false) ProductResponse productResponse){
        if (productResponse == null || productResponse.getId() == null || productResponse.getQuantity() == null){
            log.info("Missing product parameters, no quantity updated");
            return new ResponseEntity<>("Missing product parameters, no quantity updated", HttpStatus.EXPECTATION_FAILED);
        }

        //TODO update stock quantity
        return null;
    }

    @DeleteMapping("delete")
    ResponseEntity<String> delete(@RequestBody(required = false) ProductResponse productResponse) {
        if (productResponse != null && productResponse.getId() != null) {
            productRepository.deleteById(productResponse.getId());
            log.info("Product with ID: " + productResponse.getId() + " deleted");
            return new ResponseEntity<>("Product with ID: " + productResponse.getId() + " deleted", HttpStatus.ACCEPTED);
        } else {
            log.info("Incorrect request body, no ID found, no product deleted");
            return new ResponseEntity<>("Incorrect request body, no ID found, no product deleted", HttpStatus.EXPECTATION_FAILED);
        }
    }


    private Product buildProduct(String name, String price) {
        return Product.builder()
                .name(name)
                .price(BigDecimal.valueOf(Double.valueOf(price)))
                .build();
    }
}
