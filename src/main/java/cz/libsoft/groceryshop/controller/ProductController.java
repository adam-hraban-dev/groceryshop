package cz.libsoft.groceryshop.controller;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import cz.libsoft.groceryshop.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
/*
    @GetMapping("/hello")
    public String hello(){
        System.out.println(1+" jedna" + 1);

        productRepository.save(Product.builder()
                .name("Name")
                .price(BigDecimal.valueOf(123.5))
                .stock(null)
                .build());

        return "hello";
    }*/

    @PutMapping("create")
    ResponseEntity<String> create(@RequestBody(required = false) ProductResponse productResponse){

        if(productResponse == null || productResponse.getName() == null || productResponse.getPrice() == null){
            System.out.println(" --- null params, no product ---");
            return new ResponseEntity<>("no product created", HttpStatus.EXPECTATION_FAILED);
        } else {
            Product newProduct = productRepository.save(buildProduct(productResponse.getName(), productResponse.getPrice()));
            log.info("Product created: " + newProduct);
            return new ResponseEntity<>("Product created: " + newProduct, HttpStatus.CREATED);
        }

    }

    @DeleteMapping("delete")
    ResponseEntity<String> delete(@RequestBody(required = false) ProductResponse productResponse){

        if(productResponse != null && productResponse.getId() != null){
            productRepository.deleteById(productResponse.getId());
            log.info("Product with ID: " + productResponse.getId() + " deleted");
            return new ResponseEntity<>("Product with ID: " + productResponse.getId() + " deleted", HttpStatus.ACCEPTED);
        } else {
            log.info("False request body, no ID found, no product deleted");
            return new ResponseEntity<>("False request body, no ID found, no product deleted", HttpStatus.EXPECTATION_FAILED);
        }
        /*
        //Optional<Product> optionalProduct = productRepository.findById(productResponse.getId());
        System.out.println(" ---- product by ID: " + productResponse.getId() + optionalProduct.get());
*/




    }


    private Product buildProduct(String name, String price){
        return Product.builder()
                .name(name)
                .price(BigDecimal.valueOf(Double.valueOf(price)))
                .build();
    }
}
