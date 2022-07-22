package cz.libsoft.groceryshop.request;

import lombok.Data;

@Data
public class ProductRequest {

    private Long id;
    private String name;
    private String price;
    private Integer quantity;
}
