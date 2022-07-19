package cz.libsoft.groceryshop.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private ProductOrder productOrder;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    private Long quantity;
}
