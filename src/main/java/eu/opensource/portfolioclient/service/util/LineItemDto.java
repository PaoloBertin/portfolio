package eu.opensource.portfolioclient.service.util;

import java.math.BigDecimal;

public record LineItemDto(Long id,
                          String isin,
                          String name,
                          int quantity,
                          BigDecimal avgLoadPrice,
                          BigDecimal historicalValue) {

}
