package eu.opensource.portfolioclient.service.util;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class PortfolioDto {

    private Long id;

    private String name;

    private BigDecimal cash;

    private Set<LineItemDto> lineItemDtos;

    public PortfolioDto() {

    }

    public PortfolioDto(Long id, String name, BigDecimal cash, Set<LineItemDto> lineItemDtos) {

        this.id = id;
        this.name = name;
        this.cash = cash;
        this.lineItemDtos = lineItemDtos;
    }
}
