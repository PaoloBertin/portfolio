package eu.opensource.portfolioclient.service.impl;

import eu.opensource.portfolioclient.domain.Identifier;
import eu.opensource.portfolioclient.repository.IdentifierRepository;
import eu.opensource.portfolioclient.service.IdentifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service("identifierService")
public class IdentifierServiceImpl implements IdentifierService {

    private final IdentifierRepository identifierRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Identifier> getIdentifiersByWatchlist(Long watchlistId, Pageable pageable) {

        return identifierRepository.findByWatchlistId(watchlistId, pageable);
    }
}
