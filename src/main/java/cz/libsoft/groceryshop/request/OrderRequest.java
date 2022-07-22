package cz.libsoft.groceryshop.request;

import cz.libsoft.groceryshop.model.OrderStatus;
import lombok.Getter;

import java.util.Set;

@Getter
public class OrderRequest {

    private Long id;
    private OrderStatus orderStatus;
    private String customerAddress;
    private Set<OrderLineRequest> orderLines;
}
