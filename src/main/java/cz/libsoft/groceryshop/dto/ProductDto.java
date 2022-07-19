package cz.libsoft.groceryshop.dto;

import lombok.Data;

@Data
public class ProductDto {

    public Long id;
    public String name;
    public String price;
    public Long quantity;
}
