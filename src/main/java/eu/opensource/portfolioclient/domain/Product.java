package eu.opensource.portfolioclient.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Table(name = "products")
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private String isin;

    private String name;

//    private String alphaCode;

//    private String superSector;

    @JoinColumn(name = "tool_id", foreignKey = @ForeignKey(name = "products_fk_01"))
    @ManyToOne
    private Tool tool;

//    @JoinColumn(name = "watchlist_id", foreignKey = @ForeignKey(name = "products_fk_02"))
//    @ManyToOne
//    private Watchlist watchlist;
}
