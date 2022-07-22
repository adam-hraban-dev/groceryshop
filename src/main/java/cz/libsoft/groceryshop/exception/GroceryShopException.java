package cz.libsoft.groceryshop.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class GroceryShopException extends Exception {
    String code;
    List<String> errorMessages;
    String message;

    public GroceryShopException(String message, List<String> errorMessages) {
        super(message);
        this.errorMessages = errorMessages;
    }
}
