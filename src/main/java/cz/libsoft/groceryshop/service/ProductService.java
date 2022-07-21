package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.dto.ProductRequest;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest, List<String> messages) {
        if (productRequest == null || productRequest.getName() == null || productRequest.getPrice() == null) {
            messages.add("Missing parameters, no product created");
        } else {
            Product newProduct = productRepository.save(new Product(productRequest.getName(), productRequest.getPrice()));
            messages.add("Product created: " + newProduct);
        }
    }

    public void updateProduct(ProductRequest productRequest, List<String> messages) {
        if (productRequest == null || productRequest.getId() == null || productRequest.getName() == null || productRequest.getPrice() == null) {
            messages.add("Missing parameters, no product updated");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()) {
                messages.add("No product with id " + productRequest.getId() + " found");
            } else {
                optionalProduct.get().setName(productRequest.getName());
                optionalProduct.get().setPrice(BigDecimal.valueOf(Double.parseDouble(productRequest.getPrice())));
                productRepository.save(optionalProduct.get());
                messages.add("Product updated: " + optionalProduct.get());
            }
        }
    }

    public void updateProductQuantity(ProductRequest productRequest, List<String> messages) {
        if (productRequest == null || productRequest.getId() == null || productRequest.getQuantity() == null) {
            messages.add("Missing parameters, product quantity not updated");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()) {
                messages.add("No product with id " + productRequest.getId() + " found, product quantity not updated");
            } else {
                optionalProduct.get().setStockQuantity(optionalProduct.get().getStockQuantity() + productRequest.getQuantity());
                productRepository.save(optionalProduct.get());
                messages.add("Product quantity updated: " + optionalProduct.get());
            }
        }
    }

    public void deleteProduct(ProductRequest productRequest, List<String> messages) {
        if (productRequest == null || productRequest.getId() == null) {
            messages.add("Incorrect request body, no ID found, no product deleted");
        } else {
            productRepository.deleteById(productRequest.getId());
            messages.add("Product with ID: " + productRequest.getId() + " deleted");
        }
    }
}
