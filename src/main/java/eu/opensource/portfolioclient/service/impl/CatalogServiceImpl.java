package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.Product;
import eu.opensource.portfolioclient.domain.Tool;
import eu.opensource.portfolioclient.repository.ProductRepository;
import eu.opensource.portfolioclient.repository.ToolRepository;
import eu.opensource.portfolioclient.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

    private final ToolRepository toolRepository;

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getProductByIsin(String isin) {

        return productRepository.findByIsin(isin);
    }

    @Override
    public Optional<Product> getProductById(Long productId) {

        return productRepository.findById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByTool(Long toolId) {

        return productRepository.findByToolId(toolId);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProductsByToolByPage(Long toolId, Pageable pageable) {

        return productRepository.findByToolId(toolId, pageable);
    }

    @Override
    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {

        productRepository.delete(product);
    }

    @Override
    public List<Tool> getAllTools() {

        return toolRepository.findAll();
    }

    @Override
    public Optional<Tool> getToolById(Long toolId) {

        return toolRepository.findById(toolId);
    }
}
