package cz.libsoft.groceryshop.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderPaymentRequest {

    private Long id;
    private BigDecimal amountPaid;
}
