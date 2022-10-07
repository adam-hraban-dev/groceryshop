package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.OrderHeader;
import cz.libsoft.groceryshop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderHeader, Long> {

    List<OrderHeader> findByStatus(OrderStatus orderStatus);
}
