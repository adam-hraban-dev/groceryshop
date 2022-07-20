package cz.libsoft.groceryshop.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //mappedBy = "productOrder",
    @JoinColumn(name="orderId")
    private Set<OrderLine> orderLineSet;

    /*@OneToMany(mappedBy = "productOrder", cascade = CascadeType.ALL)
    private Set<OrderLine> orderLines = new HashSet<>();*/

    private OrderStatusEnu status;
    private String customerAddress;
}
