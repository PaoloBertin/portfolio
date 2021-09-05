package eu.opensource.portfolioclient.service.util;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioDto(Long id, String name, BigDecimal cash, List<LineItemDto> lineItemDtos){

}
