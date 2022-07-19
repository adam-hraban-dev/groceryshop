package cz.libsoft.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
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
    private long quantity;

    /*@OneToOne(mappedBy = "product")
    private Stock stock;*/
}
