package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
