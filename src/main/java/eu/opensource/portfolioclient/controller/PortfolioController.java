package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.controller.util.Message;
import eu.opensource.portfolioclient.controller.util.PortfolioForm;
import eu.opensource.portfolioclient.controller.util.ProductForm;
import eu.opensource.portfolioclient.domain.LineItem;
import eu.opensource.portfolioclient.domain.Portfolio;
import eu.opensource.portfolioclient.domain.Product;
import eu.opensource.portfolioclient.service.CatalogService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/portfolios")
@Controller
public class PortfolioController {

    private final MessageSource messageSource;

    private final PortfolioService portfolioService;

    private final CatalogService catalogService;

    @GetMapping("/{portfolioId}")
    public String viewPortfolioById(@PathVariable Long portfolioId, Model uiModel) {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        uiModel.addAttribute("portfolios", portfolios);

        PortfolioDto portfolio = portfolioService.getPortfolioDtoById(portfolioId);
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

    @GetMapping(value = "/{portfolioId}", params = "form")
    public String getFormUpdatePortfolio(@PathVariable Long portfolioId,  Model uiModel) {

        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        PortfolioForm portfolioForm = new PortfolioForm();
        portfolioForm.setId(portfolioId);
        portfolioForm.setName(portfolio.getName());

        uiModel.addAttribute("portfolioForm", portfolioForm);

        return "portfolios/updatePortfolio";
    }


    /**
     * Richiama form da un portafoglio per aggiungere uno strumento al portafoglio stesso
     *
     * @return view
     */
    @GetMapping("/{portfolioId}/tools")
    public String getFormAddTool(@PathVariable Long portfolioId, Model uiModel) {

        PortfolioDto portfolio = portfolioService.getPortfolioDtoById(portfolioId);
        String portfolioName = portfolio.getName();

        ProductForm productForm = new ProductForm();
        productForm.setPortfolioId(portfolioId);
        productForm.setLocalDateTime(LocalDateTime.now());

        uiModel.addAttribute("portfolioName", portfolioName);
        uiModel.addAttribute("productForm", productForm);

        return "portfolios/addTool";
    }

    @PostMapping("/{portfolioId}/tools")
    public String saveNewTool(@Valid ProductForm productForm, BindingResult result, Model uiModel,
                              RedirectAttributes redirectAttributes, Locale locale) {

        // verifica che i dati del form siano validi
        Message message;
        if (result.hasErrors()) {
            message = new Message("error", messageSource.getMessage("message.product_portfolio_save_fail", new Object[]{}, locale));
            uiModel.addAttribute("message", message);
            uiModel.addAttribute("portfolioForm", new PortfolioForm());
            return "catalog/createPortfolio";
        }
        log.debug("PortfolioForm: {}", productForm);

        // rende persistenti dati prodotto
        Portfolio portfolio = portfolioService.getPortfolioById(productForm.getPortfolioId());
        Set<LineItem> lineItems = portfolio.getLineItems();
        LineItem lineItem = new LineItem();
        lineItem.setIsin(productForm.getIsin());
        lineItem.setLoadingPrice(productForm.getPrice());
        lineItem.setQuantity(productForm.getQuantity());
        lineItems.add(lineItem);
        portfolio.setLineItems(lineItems);
        portfolio = portfolioService.savePortfolio(portfolio);

//        log.debug("Portfolio: {}", portfolioDto);

        message = new Message("success", messageSource.getMessage("message.product_portfolio_save_success", new Object[]{}, locale));
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/";
    }

    /**
     * Richiama form da una watchlist per aggiungere uno strumento ad un portafoglio
     *
     * @return view
     */
    @GetMapping("/portfolio/{isin}")
    public String getFormAddToolToPortfolio(@PathVariable String isin, Model uiModel) {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        uiModel.addAttribute("portfolios", portfolios);
        Product product = catalogService.getProductByIsin(isin)
                                        .orElseThrow();

        ProductForm productForm = new ProductForm();
        productForm.setName(product.getName());
        productForm.setIsin(isin);
        productForm.setPrice(BigDecimal.valueOf(1.0));
        productForm.setQuantity(1);
        uiModel.addAttribute("productForm", productForm);

        return "portfolios/addToolToPorfolio";
    }

    @PostMapping("/portfolio/tools")
    public String saveNewToolInPortfolio(@Valid ProductForm productForm, BindingResult result, Model uiModel,
                                         RedirectAttributes redirectAttributes, Locale locale) {

        // verifica che i dati del form siano validi
        Message message;
        if (result.hasErrors()) {
            message = new Message("error", messageSource.getMessage("message.product_portfolio_save_fail", new Object[]{}, locale));
            uiModel.addAttribute("message", message);
            uiModel.addAttribute("portfolioForm", new PortfolioForm());
            return "catalog/createPortfolio";
        }
        log.debug("PortfolioForm: {}", productForm);

        // rende persistenti dati prodotto
        Boolean saved = portfolioService.addToolToPortfolio(productForm);

        if (saved) {
            message = new Message("success", messageSource.getMessage("message.product_portfolio_save_success", new Object[]{}, locale));
            redirectAttributes.addFlashAttribute("message", message);
        } else {
            message = new Message("error", messageSource.getMessage("message.product_portfolio_save_fail", new Object[]{}, locale));
        }
        return "redirect:/";
    }
}
