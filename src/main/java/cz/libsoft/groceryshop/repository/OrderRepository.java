package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ProductOrder, Long> {
}
