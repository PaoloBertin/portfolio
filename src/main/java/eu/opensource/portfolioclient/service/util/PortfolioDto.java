package eu.opensource.portfolioclient.service.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record PortfolioDto(Long id, String name, BigDecimal cash, Set<LineItemDto> lineItemDtos){

}
