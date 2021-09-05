package eu.opensource.portfolioclient.repository;

import eu.opensource.portfolioclient.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByIsin(String isin);

    List<Product> findByToolId(Long toolId);

    Page<Product> findByToolId(Long toolId, Pageable pageable);
}
