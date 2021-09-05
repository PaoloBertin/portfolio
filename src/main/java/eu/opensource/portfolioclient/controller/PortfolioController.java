package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.PortfolioService;
import eu.opensource.portfolioclient.service.util.PortfolioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/portfolios")
@Controller
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{portfolioId}")
    public String viewPortfolioById(@PathVariable Long portfolioId, Model uiModel) {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        uiModel.addAttribute("portfolios", portfolios);

        PortfolioDto portfolio = portfolioService.getPortfolioById(portfolioId);
        uiModel.addAttribute("portfolio", portfolio);

        return "portfolios/portfolioView";
    }
}
