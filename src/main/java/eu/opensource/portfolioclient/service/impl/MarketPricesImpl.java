package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.MarketPrices;
import eu.opensource.portfolioclient.repository.MarketPricesRepository;
import eu.opensource.portfolioclient.service.MarketPricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("marketPrices")
public class MarketPricesImpl implements MarketPricesService {

    private final MarketPricesRepository marketPricesRepository;

    @Override
    public MarketPrices getMarketPricesByIsin(String isin) {

        return marketPricesRepository.findByIsin(isin);
    }
}
