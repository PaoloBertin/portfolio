package eu.opensource.portfolioclient.service;

import eu.opensource.portfolioclient.domain.Product;
import eu.opensource.portfolioclient.domain.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CatalogService {

    Optional<Product> getProductByIsin(String isin);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByTool(Long toolId);

    Page<Product> getProductsByToolByPage(Long toolId, Pageable pageable);

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    List<Tool> getAllTools();

    Optional<Tool> getToolById(Long toolId);
}
