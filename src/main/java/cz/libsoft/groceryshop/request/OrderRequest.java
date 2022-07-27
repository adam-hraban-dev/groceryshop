package cz.libsoft.groceryshop.request;

import cz.libsoft.groceryshop.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class OrderRequest {

    private Long id;
    private OrderStatus orderStatus;
    private String customerAddress;
    private Set<OrderLineRequest> orderLines;
}
