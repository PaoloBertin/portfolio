package eu.opensource.portfolioclient.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "line_items")
@Entity
public class LineItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isin;

    private int quantity;

    @Column(name = "loading_price")
    private BigDecimal loadingPrice;
}
