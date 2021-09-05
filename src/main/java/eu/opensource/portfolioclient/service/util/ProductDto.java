package eu.opensource.portfolioclient.service.util;

import eu.opensource.portfolioclient.domain.Tool;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(Long id,
                         String isin,
                         String name,
                         Tool tool,
                         BigDecimal lastPrice,
                         Double percChange,
                         LocalDateTime now,
                         BigDecimal openingPrice) {

}
