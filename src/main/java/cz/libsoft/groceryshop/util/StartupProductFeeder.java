package cz.libsoft.groceryshop.util;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartupProductFeeder {

    private final ProductRepository productRepository;

    @Bean
    CommandLineRunner runner(){
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Bread");
        product1.setQuantity(150L);
        product1.setPrice(BigDecimal.valueOf(20L));
        productList.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Milk");
        product2.setQuantity(90L);
        product2.setPrice(BigDecimal.valueOf(35L));
        productList.add(product2);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Butter");
        product3.setQuantity(55L);
        product3.setPrice(BigDecimal.valueOf(120L));
        productList.add(product3);

        return args -> this.productRepository.saveAll(productList);
    }
}
