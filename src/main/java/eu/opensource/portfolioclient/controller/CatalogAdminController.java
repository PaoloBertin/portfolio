package eu.opensource.portfolioclient.controller;

import eu.opensource.portfolioclient.controller.util.Message;
import eu.opensource.portfolioclient.controller.util.ProductForm;
import eu.opensource.portfolioclient.domain.Product;
import eu.opensource.portfolioclient.domain.Tool;
import eu.opensource.portfolioclient.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/catalog")
@Controller
public class CatalogAdminController {

    private final MessageSource messageSource;

    private final CatalogService catalogService;

    @GetMapping("/{toolId}/products")
    public String getProductsByCategory(@PathVariable Long toolId, @PageableDefault Pageable pageable, Model uiModel) {

        List<Tool> tools = catalogService.getAllTools();
        uiModel.addAttribute("tools", tools);

        Optional<Tool> tool = catalogService.getToolById(toolId);

        String toolName = tool.map(Tool::getName)
                              .orElseThrow();

        uiModel.addAttribute("toolName", toolName);

        Page<Product> products = catalogService.getProductsByToolByPage(toolId, pageable);
        uiModel.addAttribute("page", pageable.getPageNumber());
        uiModel.addAttribute("size", pageable.getPageSize());
        uiModel.addAttribute("products", products);

        return "catalog/productsListAdmin";
    }

    @GetMapping("/tools/products")
    public String getCreateFormProduct(Model uiModel) {

        List<Tool> tools = catalogService.getAllTools();
        uiModel.addAttribute("tools", tools);

        uiModel.addAttribute("productForm", new ProductForm());

        return "catalog/createProduct";
    }

    @PostMapping("/tools/products")
    public String createProduct(@Valid ProductForm productForm, BindingResult result, Model uiModel, RedirectAttributes redirectAttributes,
                                Locale locale) {

        // verifica che i dati del form siano validi
        Message message;
        if (result.hasErrors()) {
            message = new Message("error", messageSource.getMessage("message.product_save_fail", new Object[]{}, locale));
            uiModel.addAttribute("message", message);
            uiModel.addAttribute("productForm", new ProductForm());
            return "catalog/createProduct";
        }

        log.info("ProductForm: {}", productForm);

        // rende persistenti dati prodotto
        Tool tool = catalogService.getToolById(productForm.getToolId())
                                  .orElseThrow();
        Product product = new Product();
        product.setName(productForm.getName());
        product.setIsin(productForm.getIsin());
        product.setTool(tool);
        product = catalogService.saveProduct(product);
        log.info("Product: {}", product);

        message = new Message("success", messageSource.getMessage("message.product_save_success", new Object[]{}, locale));
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/";
    }

    @GetMapping("/tools/{isin}")
    public String getUpdaFormProduct(@PathVariable String isin, Model uiModel) {

        List<Tool> tools = catalogService.getAllTools();
        uiModel.addAttribute("tools", tools);

        Product product = catalogService.getProductByIsin(isin)
                                        .orElseThrow();
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setIsin(product.getIsin());
        productForm.setName(product.getName());
        productForm.setToolId(product.getTool()
                                     .getId());

        uiModel.addAttribute("productForm", productForm);

        return "catalog/editProduct";
    }

    @PutMapping("/tools/products")
    public String updateProduct(@Valid ProductForm productForm, BindingResult result, Model uiModel, RedirectAttributes redirectAttributes,
                                Locale locale) {

        // verifica che i dati del form siano validi
        Message message;
        if (result.hasErrors()) {
            message = new Message("error", messageSource.getMessage("message.product_save_fail", new Object[]{}, locale));
            uiModel.addAttribute("message", message);
            uiModel.addAttribute("productForm", new ProductForm());
            return "catalog/updateProduct";
        }

        // rende persistenti dati prodotto
        Tool tool = catalogService.getToolById(productForm.getToolId())
                                  .orElseThrow();
        Product product = new Product();
        product.setId(productForm.getId());
        product.setName(productForm.getName());
        product.setIsin(productForm.getIsin());
        product.setTool(tool);
        product = catalogService.saveProduct(product);
        log.info("Product: {}", product);

        message = new Message("success", messageSource.getMessage("message.product_save_success", new Object[]{}, locale));
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/";
    }

    @DeleteMapping("/tools/products")
    public String deleteProduct(@RequestParam String isin) {

        Product product = catalogService.getProductByIsin(isin)
                                        .orElseThrow();

        catalogService.deleteProduct(product);

        return "redirect:/";
    }
}
