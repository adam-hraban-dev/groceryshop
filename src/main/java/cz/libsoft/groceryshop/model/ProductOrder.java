package cz.libsoft.groceryshop.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
public class ProductOrder {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productOrder")
    private List<OrderLine> orderLine;

    private OrderStatusEnu status;
    private String customerAddress;
}
