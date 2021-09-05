package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.util.PortfolioDto;

import java.util.List;

public interface PortfolioService {

    PortfolioDto getPortfolioById(Long portfolioId);

    List<Portfolio> getAllPortfolios();

    Portfolio savePortfolio(Portfolio portfolio);

    Portfolio updatePortfolio(Portfolio portfolio);
}
