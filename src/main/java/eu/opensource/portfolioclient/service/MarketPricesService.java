package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.domain.MarketPrices;

public interface MarketPricesService {

    MarketPrices getMarketPricesByIsin(String isin);
}
