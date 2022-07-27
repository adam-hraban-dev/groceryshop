package cz.libsoft.groceryshop.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class OrderPaymentRequest {

    private Long id;
    private BigDecimal amountPaid;
}
