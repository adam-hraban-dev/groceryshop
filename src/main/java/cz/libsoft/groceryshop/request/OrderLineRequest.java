package cz.libsoft.groceryshop.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineRequest {
    private Long id;
    private Long productOrderId;
    private Long productId;
    private Integer quantity;
}
