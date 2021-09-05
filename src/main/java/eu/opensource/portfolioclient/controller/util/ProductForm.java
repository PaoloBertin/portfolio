package eu.opensource.portfolioclient.controller.util;

import lombok.Data;

@Data
public class ProductForm {

    private Long id;

    private String isin;

    private String name;

    private Long toolId;
}
