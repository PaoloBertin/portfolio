package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.controller.util.ProductForm;
import eu.opensource.portfolioclient.domain.LineItem;
import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.repository.PortfolioRepository;
import eu.opensource.portfolioclient.service.CatalogService;
import eu.opensource.portfolioclient.service.MarketPricesService;
import eu.opensource.portfolioclient.service.PortfolioService;
import eu.opensource.portfolioclient.service.util.LineItemDto;
import eu.opensource.portfolioclient.service.util.PortfolioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    private final CatalogService productService;

    private final MarketPricesService marketPricesService;

    @Override
    public PortfolioDto getPortfolioDtoById(Long portfolioId) {

        return portfolioRepository.findById(portfolioId)
                                  .map((portfolio) -> {
                                      Set<LineItem> lineItems = portfolio.getLineItems();
                                      Set<LineItemDto> lineItemDtos = new HashSet<>();
                                      lineItems.forEach(lineItem -> {
                                          Long lineItemId = lineItem.getId();
                                          String isin = lineItem.getIsin();
                                          String productName = productService.getProductByIsin(lineItem.getIsin())
                                                                             .orElseThrow()
                                                                             .getName();
                                          int quantity = lineItem.getQuantity();
                                          BigDecimal loadingPrice = lineItem.getLoadingPrice();
                                          BigDecimal historicalValue = loadingPrice.multiply(BigDecimal.valueOf(quantity));
                                          BigDecimal openingPrice = marketPricesService.getMarketPricesByIsin(lineItem.getIsin())
                                                                                       .getOpeningPrice();
                                          BigDecimal lastPrice = marketPricesService.getMarketPricesByIsin(lineItem.getIsin())
                                                                                    .getLastPrice();
                                          BigDecimal actualValue = lastPrice.multiply(BigDecimal.valueOf(lineItem.getQuantity()));
                                          double percChange = ((lastPrice.add(loadingPrice.negate())).divide(loadingPrice, 4, RoundingMode.HALF_DOWN)).doubleValue();
                                          LineItemDto lineItemDto = new LineItemDto(lineItemId,
                                                                                    isin,
                                                                                    productName,
                                                                                    quantity,
                                                                                    loadingPrice,
                                                                                    historicalValue,
                                                                                    openingPrice,
                                                                                    lastPrice,
                                                                                    actualValue,
                                                                                    percChange);
                                          lineItemDtos.add(lineItemDto);
                                      });
                                      PortfolioDto portfolioDto = new PortfolioDto(portfolio.getId(), portfolio.getName(), portfolio.getCash(), lineItemDtos);
                                      return new PortfolioDto(portfolio.getId(), portfolio.getName(), portfolio.getCash(), lineItemDtos);
                                  })
                                  .orElseThrow();
    }

    @Override
    public Portfolio getPortfolioById(Long portfolioId) {

        return portfolioRepository.findById(portfolioId)
                                  .orElseThrow();
    }

    @Override
    public List<Portfolio> getAllPortfolios() {

        return portfolioRepository.findAll();
    }

    @Override
    public Boolean addToolToPortfolio(ProductForm productForm) {

        Boolean addTool = false;
        Portfolio portfolio = getPortfolioById(productForm.getPortfolioId());
        Set<LineItem> lineItems = portfolio.getLineItems();
        LineItem lineItem = new LineItem();
        lineItem.setIsin(productForm.getIsin());
        lineItem.setLoadingPrice(productForm.getPrice());
        lineItem.setQuantity(productForm.getQuantity());

        if (lineItems.contains(lineItem)) {
            lineItems.forEach(element -> {
                if (element.equals(lineItem)) {
                    int quantity = element.getQuantity();
                    BigDecimal price = element.getLoadingPrice();

                    int newQuantity = lineItem.getQuantity();
                    BigDecimal newPrice = lineItem.getLoadingPrice();

                    BigDecimal product1 = price.multiply(BigDecimal.valueOf(quantity));
                    BigDecimal product2 = newPrice.multiply(BigDecimal.valueOf(newQuantity));
                    BigDecimal sum = product1.add(product2);
                    BigDecimal newPmc = sum.divide(BigDecimal.valueOf(quantity + newQuantity));

                    element.setLoadingPrice(newPmc);
                    element.setQuantity(quantity + newQuantity);
                }
            });
        } else {
            lineItems.add(lineItem);
        }
        portfolio.setLineItems(lineItems);
        portfolio = savePortfolio(portfolio);
        log.debug("Portfolio: {}", portfolio);
        addTool = true;

        return addTool;
    }

    @Override
    public Portfolio savePortfolio(Portfolio portfolio) {

        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio) {

        return null;
    }

    @Override
    public void deletePortfolio(Portfolio portfolio) {

        portfolioRepository.delete(portfolio);
    }

}
