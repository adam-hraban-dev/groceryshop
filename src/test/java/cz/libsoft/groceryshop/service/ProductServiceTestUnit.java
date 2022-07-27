package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import cz.libsoft.groceryshop.request.ProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
public class ProductServiceTestUnit {

    @Autowired
    ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private static final String PRODUCT_NAME = "New product";
    private static final String PRODUCT_PRICE = "125";

    @Test
    void productNotCreated() {
        ProductRequest productRequest = ProductRequest.builder()
                .price(PRODUCT_PRICE)
                .build();

        Product productFromRepository = new Product();
        productFromRepository.setPrice(BigDecimal.valueOf(Long.parseLong(PRODUCT_PRICE)));
        productFromRepository.setName(PRODUCT_NAME);
        productFromRepository.setId(10L);

        Mockito.when(productRepository.save(new Product(PRODUCT_NAME, PRODUCT_PRICE))
        ).thenReturn(productFromRepository);

        try {
            productService.createProduct(productRequest, new ArrayList<>());
        } catch (GroceryShopException gse) {
            Assertions.assertEquals(gse.getErrorMessages().get(0), "Missing parameters, no product created");
        }
    }
}
