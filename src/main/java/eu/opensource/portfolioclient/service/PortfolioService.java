package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.controller.util.ProductForm;
import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.util.PortfolioDto;

import java.util.List;

public interface PortfolioService {

    PortfolioDto getPortfolioDtoById(Long portfolioId);

    Portfolio getPortfolioById(Long portfolioId);

    List<Portfolio> getAllPortfolios();

    Boolean addToolToPortfolio(ProductForm productForm);

    Portfolio savePortfolio(Portfolio portfolio);

    Portfolio updatePortfolio(Portfolio portfolio);

    void deletePortfolio(Portfolio portfolio);
}
