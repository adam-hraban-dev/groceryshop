package cz.libsoft.groceryshop.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    public Long id;
    public String name;
    public String price;
}
