package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.PortfolioService;
import eu.opensource.portfolioclient.service.util.PortfolioDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
class PortfolioServiceImplTest {

    @Autowired
    private PortfolioService portfolioService;

    @Test
    void getPortfolioByIdTest() {

        Portfolio portfolio = portfolioService.getPortfolioById(1L);

        assertThat(portfolio.getName()).isEqualTo("Portafoglio 1");

    }

    @Test
    void getAllPortfoliosTest() {

    }

    @Test
    void savePortfolio() {

    }

    @Test
    void updatePortfolio() {

    }
}