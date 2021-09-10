package eu.opensource.portfolioclient.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "market_prices")
@Entity
public class MarketPrices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isin;

    @Column(name = "openng_price")
    private BigDecimal openingPrice;

    @Column(name = "last_price")
    private BigDecimal lastPrice;
}
