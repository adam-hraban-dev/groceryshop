package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.dto.ProductDto;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDto productDto) {
        return productRepository.save(Product.builder()
                .name(productDto.getName())
                .price(BigDecimal.valueOf(Double.parseDouble(productDto.getPrice())))
                .build());
    }
}
