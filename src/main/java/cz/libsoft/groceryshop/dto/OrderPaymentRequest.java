package cz.libsoft.groceryshop.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderPaymentRequest {

    private Long id;
    private BigDecimal amountPaid;
}
