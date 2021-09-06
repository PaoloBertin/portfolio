package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.controller.util.Message;
import eu.opensource.portfolioclient.controller.util.PortfolioForm;
import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.service.PortfolioService;
import eu.opensource.portfolioclient.service.util.PortfolioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/portfolios")
@Controller
public class PortfolioController {

    private final MessageSource messageSource;

    private final PortfolioService portfolioService;

    @GetMapping("/{portfolioId}")
    public String viewPortfolioById(@PathVariable Long portfolioId, Model uiModel) {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        uiModel.addAttribute("portfolios", portfolios);

        PortfolioDto portfolio = portfolioService.getPortfolioById(portfolioId);
        uiModel.addAttribute("portfolio", portfolio);

        return "portfolios/portfolioView";
    }

    @GetMapping
    public String getFormCreatePortfolio(Model uiModel) {

        uiModel.addAttribute("portfolioForm", new PortfolioForm());

        return "portfolios/createPortfolio";
    }

    @PostMapping
    public String createPortfolio(@Valid PortfolioForm portfolioForm, BindingResult result, Model uiModel,
                                  RedirectAttributes redirectAttributes,
                                  Locale locale) {

        // verifica che i dati del form siano validi
        Message message;
        if (result.hasErrors()) {
            message = new Message("error", messageSource.getMessage("message.portfolio_save_fail", new Object[]{}, locale));
            uiModel.addAttribute("message", message);
            uiModel.addAttribute("portfolioForm", new PortfolioForm());
            return "catalog/createPortfolio";
        }
        log.debug("PortfolioForm: {}", portfolioForm);

        // rende persistenti dati prodotto
        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioForm.getName());
        portfolio.setCash(portfolioForm.getCash());
        portfolio = portfolioService.savePortfolio(portfolio);
        log.debug("Portfolio: {}", portfolio);

        message = new Message("success", messageSource.getMessage("message.portfolio_save_success", new Object[]{}, locale));
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/";
    }

}
