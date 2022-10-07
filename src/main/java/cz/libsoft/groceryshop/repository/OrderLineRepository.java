package cz.libsoft.groceryshop.repository;

import cz.libsoft.groceryshop.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
