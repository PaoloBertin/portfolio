package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.Identifier;
import eu.opensource.portfolioclient.domain.Product;
import eu.opensource.portfolioclient.domain.Watchlist;
import eu.opensource.portfolioclient.repository.ProductRepository;
import eu.opensource.portfolioclient.repository.WatchlistRepository;
import eu.opensource.portfolioclient.service.CatalogService;
import eu.opensource.portfolioclient.service.IdentifierService;
import eu.opensource.portfolioclient.service.WatchListService;
import eu.opensource.portfolioclient.service.util.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("watchListService")
public class WatchListServiceImpl implements WatchListService {

    private final CatalogService catalogService;

    private final IdentifierService identifierService;

    private final WatchlistRepository watchlistRepository;

    @Override
    public Watchlist getWatchlist(Long watchlistId, Pageable pageable) {

        Watchlist watchlist = watchlistRepository.findById(watchlistId)
                                                 .orElseThrow();
        Page<Identifier> identifiers = identifierService.getIdentifiersByWatchlist(watchlistId, pageable);

        List<ProductDto> productDtos = new ArrayList<>();

        identifiers.forEach(identifier -> {
            Product product = catalogService.getProductByIsin(identifier.getIsin())
                                               .orElseThrow();
            ProductDto productDto = new ProductDto(product.getId(),
                                                   product.getIsin(),
                                                   product.getName(),
                                                   product.getTool(),
                                                   BigDecimal.ZERO,
                                                   0.0,
                                                   LocalDateTime.now(),
                                                   BigDecimal.ZERO);
            productDtos.add(productDto);
        });
//        Page<ProductDto> productDtoPage = new PageImpl<>(productDtos, pageable, total);
        Page<ProductDto> productDtoPage = new PageImpl<>(productDtos, pageable, identifiers.getTotalElements());
        watchlist.setProducts(productDtoPage);

        return watchlist;
    }
}
