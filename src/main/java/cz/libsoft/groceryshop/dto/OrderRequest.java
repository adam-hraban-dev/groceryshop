package cz.libsoft.groceryshop.dto;

import cz.libsoft.groceryshop.model.OrderStatus;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Getter
public class OrderRequest {

    private Long id;
    private OrderStatus orderStatus;
    private String customerAddress;
    private Set<OrderLineDto> orderLines;
}
