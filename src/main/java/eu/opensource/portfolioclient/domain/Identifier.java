package eu.opensource.portfolioclient.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table( name = "identifiers")
@Entity
public class Identifier implements Serializable {

    @Id
    private String isin;

    @JoinColumn(name = "watchlist_id", foreignKey = @ForeignKey(name = "identifiers_fk_01"))
    @ManyToOne
    private Watchlist watchlist;

}
