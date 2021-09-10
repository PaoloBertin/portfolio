package eu.opensource.portfolioclient.repository;

import eu.opensource.portfolioclient.domain.MarketPrices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPricesRepository extends JpaRepository<MarketPrices, Long> {

    MarketPrices findByIsin(String isin);
}
