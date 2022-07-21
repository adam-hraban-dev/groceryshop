package cz.libsoft.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer stockQuantity;

    /*@OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProductSet;*/
}
