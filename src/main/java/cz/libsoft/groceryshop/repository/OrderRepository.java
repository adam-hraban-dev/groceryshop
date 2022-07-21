package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.Order;
import cz.libsoft.groceryshop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus orderStatus);
}
