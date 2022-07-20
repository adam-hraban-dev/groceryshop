package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import cz.libsoft.groceryshop.dto.ProductDto;
import cz.libsoft.groceryshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
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

    private final ProductRepository productRepository;

    @PutMapping("create")
    ResponseEntity<String> create(@RequestBody(required = false) ProductDto productDto) {
        if (productDto == null || productDto.getName() == null || productDto.getPrice() == null) {
            log.info("Missing parameters, no product created");
            return new ResponseEntity<>("Missing parameters, no product created", HttpStatus.EXPECTATION_FAILED);
        } else {
            Product newProduct = productRepository.save(buildProduct(productDto.getName(), productDto.getPrice()));
            log.info("Product created: " + newProduct);
            return new ResponseEntity<>("Product created: " + newProduct, HttpStatus.CREATED);
        }
    }

    @PostMapping("update")
    ResponseEntity<String> update(@RequestBody(required = false) ProductDto productDto) {
        if (productDto == null || productDto.getId() == null || productDto.getName() == null || productDto.getPrice() == null) {
            log.info("Missing parameters, no product updated");
            return new ResponseEntity<>("Missing parameters, no product updated", HttpStatus.EXPECTATION_FAILED);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
            if (optionalProduct.isEmpty()) {
                log.info("No product with id " + productDto.getId() + " found");
                return new ResponseEntity<>("No product with id " + productDto.getId() + " found", HttpStatus.EXPECTATION_FAILED);
            } else {
                optionalProduct.get().setName(productDto.getName());
                optionalProduct.get().setPrice(BigDecimal.valueOf(Double.parseDouble(productDto.getPrice())));
                productRepository.save(optionalProduct.get());
                log.info("Product updated: " + optionalProduct.get());
                return new ResponseEntity<>("Product updated: " + optionalProduct.get(), HttpStatus.ACCEPTED);
            }
        }
    }

    @PostMapping("update-quantity")
    ResponseEntity<String> updateQuantity(@RequestBody(required = false) ProductDto productDto) {
        if (productDto == null || productDto.getId() == null || productDto.getQuantity() == null) {
            log.info("Missing parameters, no quantity updated");
            return new ResponseEntity<>("Missing parameters, no quantity updated", HttpStatus.EXPECTATION_FAILED);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
            if (optionalProduct.isEmpty()) {
                log.info("No product with id " + productDto.getId() + " found, no quantity updated");
                return new ResponseEntity<>("No product with id " + productDto.getId() + " found, no quantity updated", HttpStatus.EXPECTATION_FAILED);
            } else {
                optionalProduct.get().setQuantity(optionalProduct.get().getQuantity() + productDto.getQuantity());
                productRepository.save(optionalProduct.get());
                log.info("Product quantity updated: " + optionalProduct.get());
                return new ResponseEntity<>("Product quantity updated: " + optionalProduct.get(), HttpStatus.ACCEPTED);
            }
        }
    }

    @DeleteMapping("delete")
    ResponseEntity<String> delete(@RequestBody(required = false) ProductDto productDto) {
        if (productDto == null || productDto.getId() == null) {
            log.info("Incorrect request body, no ID found, no product deleted");
            return new ResponseEntity<>("Incorrect request body, no ID found, no product deleted", HttpStatus.EXPECTATION_FAILED);
        } else {
            productRepository.deleteById(productDto.getId());
            log.info("Product with ID: " + productDto.getId() + " deleted");
            return new ResponseEntity<>("Product with ID: " + productDto.getId() + " deleted", HttpStatus.ACCEPTED);
        }
    }


    private Product buildProduct(String name, String price) {
        return Product.builder()
                .name(name)
                .price(BigDecimal.valueOf(Double.valueOf(price)))
                .build();
    }
}
