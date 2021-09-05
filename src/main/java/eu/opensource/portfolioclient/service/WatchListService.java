package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.domain.Watchlist;
import org.springframework.data.domain.Pageable;

public interface WatchListService {

    Watchlist getWatchlist(Long watchlistId, Pageable pageable);

}
