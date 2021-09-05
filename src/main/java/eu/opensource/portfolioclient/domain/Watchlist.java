package eu.opensource.portfolioclient.domain;

import eu.opensource.portfolioclient.service.util.ProductDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "watchlists")
@Entity
public class Watchlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Transient
    private Page<ProductDto> products;
}
