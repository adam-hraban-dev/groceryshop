package cz.libsoft.groceryshop.dto;

import lombok.Data;

@Data
public class OrderLineDto {
    private Long id;
    private Long productOrderId;
    private Long productId;
    private Long quantity;
}
