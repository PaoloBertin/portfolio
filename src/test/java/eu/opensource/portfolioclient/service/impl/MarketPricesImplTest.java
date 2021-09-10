package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.MarketPrices;
import eu.opensource.portfolioclient.service.MarketPricesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MarketPricesImplTest {

    @Autowired
    private MarketPricesService marketPricesService;

    @Test
    void getOpeningPriceMarketPricesByIsinTest() {

        String isin = "NL0015435975";
        MarketPrices marketPrices = marketPricesService.getMarketPricesByIsin(isin);

        assertThat(marketPrices.getOpeningPrice()).isEqualByComparingTo(BigDecimal.valueOf(11.8950));
    }

    @Test
    void getLastPriceMarketPricesByIsinTest() {

        String isin = "NL0015435975";
        MarketPrices marketPrices = marketPricesService.getMarketPricesByIsin(isin);

        assertThat(marketPrices.getLastPrice()).isEqualByComparingTo(BigDecimal.valueOf(11.8300));
    }
}