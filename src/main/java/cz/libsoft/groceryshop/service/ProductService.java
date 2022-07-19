package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.dto.ProductDto;
import cz.libsoft.groceryshop.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    public Product createProduct(ProductDto productDto);
}
