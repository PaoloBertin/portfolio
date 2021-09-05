package eu.opensource.portfolioclient.controller.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioForm {

    private Long id;

    private String name;

    private BigDecimal cash;
}
