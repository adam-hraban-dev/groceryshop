package cz.libsoft.groceryshop.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GroceryShopResponse {
    private GroceryShopResponseCode code;
    private GroceryShopResponseStatus status;
    private List<String> messages;
}
