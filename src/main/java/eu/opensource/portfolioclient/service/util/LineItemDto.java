package eu.opensource.portfolioclient.service.util;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class LineItemDto {

    private Long id;

    private String isin;

    private String productName;

    private int quantity;

    private BigDecimal avgLoadPrice;

    private BigDecimal historicalValue;

    private BigDecimal openingPrice;

    private BigDecimal lastPrice;

    private BigDecimal actualValue;

    private double percentChange;

    public LineItemDto(Long id,
                       String isin,
                       String productName,
                       int quantity,
                       BigDecimal avgLoadPrice,
                       BigDecimal historicalValue,
                       BigDecimal openingPrice,
                       BigDecimal lastPrice,
                       BigDecimal actualValue,
                       double percentChange
    ) {

        this.id = id;
        this.isin = isin;
        this.productName = productName;
        this.quantity = quantity;
        this.avgLoadPrice = avgLoadPrice;
        this.historicalValue = historicalValue;
        this.openingPrice = openingPrice;
        this.lastPrice = lastPrice;
        this.actualValue = actualValue;
        this.percentChange = percentChange;
    }
}
