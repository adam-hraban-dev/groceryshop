package cz.libsoft.groceryshop.service;

import cz.libsoft.groceryshop.exception.GroceryShopException;
import cz.libsoft.groceryshop.request.ProductRequest;
import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest, List<String> messages) throws GroceryShopException {
        if (productRequest == null || productRequest.getName() == null || productRequest.getPrice() == null) {
            messages.add("Missing parameters, no product created");
            throw new GroceryShopException("Missing parameters, no product created", messages);
        } else {
            Product newProduct = productRepository.save(new Product(productRequest.getName(), productRequest.getPrice()));
            messages.add("Product created, id: " + newProduct.getId());
            log.info("Product created, id: " + newProduct.getId());
            return newProduct;
        }
    }

    public Product updateProduct(ProductRequest productRequest, List<String> messages) throws GroceryShopException {
        if (productRequest == null || productRequest.getId() == null || productRequest.getName() == null || productRequest.getPrice() == null) {
            messages.add("Missing parameters, no product updated");
            throw new GroceryShopException("Missing parameters, no product updated", messages);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()) {
                messages.add("No product with id " + productRequest.getId() + " found");
                throw new GroceryShopException("No product with id " + productRequest.getId() + " found", messages);
            } else {
                optionalProduct.get().setName(productRequest.getName());
                optionalProduct.get().setPrice(BigDecimal.valueOf(Double.parseDouble(productRequest.getPrice())));
                Product updatedProduct = productRepository.save(optionalProduct.get());
                messages.add("Product updated: " + optionalProduct.get().getId());
                log.info("Product updated: " + optionalProduct.get().getId());
                return updatedProduct;
            }
        }
    }

    public void updateProductQuantity(ProductRequest productRequest, List<String> messages) throws GroceryShopException {
        if (productRequest == null || productRequest.getId() == null || productRequest.getQuantity() == null) {
            messages.add("Missing parameters, product quantity not updated");
            throw new GroceryShopException("Missing parameters, product quantity not updated", messages);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()) {
                messages.add("No product with id " + productRequest.getId() + " found, product quantity not updated");
                throw new GroceryShopException("No product with id " + productRequest.getId() + " found, product quantity not updated", messages);
            } else {
                int stockQty = optionalProduct.get().getStockQuantity() == null ? 0 : optionalProduct.get().getStockQuantity();
                optionalProduct.get().setStockQuantity(stockQty + productRequest.getQuantity());
                productRepository.save(optionalProduct.get());
                messages.add("Product quantity updated, id: " + optionalProduct.get().getId());
                log.info("Product quantity updated, id: " + optionalProduct.get().getId());
            }
        }
    }

    public void deleteProduct(ProductRequest productRequest, List<String> messages) throws GroceryShopException {
        if (productRequest == null || productRequest.getId() == null) {
            messages.add("Incorrect request body, no ID found, no product deleted");
            throw new GroceryShopException("Incorrect request body, no ID found, no product deleted", messages);
        } else {
            productRepository.deleteById(productRequest.getId());
            messages.add("Product id " + productRequest.getId() + " deleted");
            log.info("Product id " + productRequest.getId() + " deleted");
        }
    }
}
