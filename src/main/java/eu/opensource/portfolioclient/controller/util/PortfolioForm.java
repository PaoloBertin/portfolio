package eu.opensource.portfolioclient.controller.util;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class PortfolioForm {

    private Long id;

    private String name;

//    @Positive
    private BigDecimal cash;
}
