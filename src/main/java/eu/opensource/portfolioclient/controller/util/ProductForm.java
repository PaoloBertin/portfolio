package eu.opensource.portfolioclient.controller.util;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductForm {

    private Long id; // productId

    private String isin;

    private String name; // product name

    private Integer quantity;

    private BigDecimal price;

    private LocalDateTime localDateTime;

    private Long portfolioId;

    private Long toolId;
}
