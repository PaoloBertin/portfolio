package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class WelcomeController {

    private final PortfolioService portfolioService;

    @GetMapping
    public String viewWelcome(Model uiModel) {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();

        uiModel.addAttribute("portfolios", portfolios);

        return "welcome";
    }
}
