package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
