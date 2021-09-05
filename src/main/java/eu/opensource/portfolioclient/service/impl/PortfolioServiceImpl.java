package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.LineItem;
import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.repository.PortfolioRepository;
import eu.opensource.portfolioclient.service.PortfolioService;
import eu.opensource.portfolioclient.service.CatalogService;
import eu.opensource.portfolioclient.service.util.LineItemDto;
import eu.opensource.portfolioclient.service.util.PortfolioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    private final CatalogService productService;

    @Override
    public PortfolioDto getPortfolioById(Long portfolioId) {

        Optional<Portfolio> portfolioTest = portfolioRepository.findById(portfolioId);

        return portfolioRepository.findById(portfolioId)
                                  .map((portfolio) -> {
                                      List<LineItem> lineItems = portfolio.getLineItems();
                                      List<LineItemDto> lineItemDtos = new ArrayList<>();
                                      lineItems.forEach(lineItem -> {
                                          LineItemDto lineItemDto = new LineItemDto(lineItem.getId(),
                                                                                    lineItem.getIsin(),
                                                                                    productService.getProductByIsin(lineItem.getIsin())
                                                                                                  .get() // TODO
                                                                                                  .getName(),
                                                                                    lineItem.getQuantity(),
                                                                                    lineItem.getLoadingPrice(),
                                                                                    lineItem.getLoadingPrice()
                                                                                            .multiply(BigDecimal.valueOf(lineItem.getQuantity())));
                                          lineItemDtos.add(lineItemDto);
                                      });
                                      return new PortfolioDto(portfolio.getId(), portfolio.getName(), portfolio.getCash(), lineItemDtos);
                                  })
                                  .orElseThrow();

    }

    @Override
    public List<Portfolio> getAllPortfolios() {

        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio savePortfolio(Portfolio portfolio) {

        return null;
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio) {

        return null;
    }

}
