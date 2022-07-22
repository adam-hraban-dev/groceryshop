package cz.libsoft.groceryshop.request;

import lombok.Data;

@Data
public class OrderLineRequest {
    private Long id;
    private Long productOrderId;
    private Long productId;
    private Integer quantity;
}
