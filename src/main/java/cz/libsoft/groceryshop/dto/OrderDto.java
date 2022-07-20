package cz.libsoft.groceryshop.dto;

import cz.libsoft.groceryshop.model.OrderStatusEnu;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDto {

    private Long id;
    private OrderStatusEnu orderStatus;
    private String customerAddress;
    private Set<OrderLineDto> orderLines;
}
