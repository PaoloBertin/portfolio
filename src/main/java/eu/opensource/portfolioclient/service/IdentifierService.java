package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.domain.Identifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IdentifierService {

    Page<Identifier> getIdentifiersByWatchlist(Long watchlistId, Pageable pageable);
}
