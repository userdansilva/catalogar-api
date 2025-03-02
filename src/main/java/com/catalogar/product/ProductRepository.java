package com.catalogar.product;

import com.catalogar.catalog.Catalog;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, UUID> {

    boolean existsByNameAndCatalog(String name, Catalog catalog);

    boolean existsBySlugAndCatalog(String slug, Catalog catalog);

    Page<Product> findAllByCatalog(Catalog catalog, Pageable pageable);

    Optional<Product> findByIdAndCatalog(UUID id, Catalog catalog);

    boolean existsByNameAndIdNotAndCatalog(String name, UUID id, Catalog catalog);

    boolean existsBySlugAndIdNotAndCatalog(String slug, UUID id, Catalog catalog);

    @Transactional
    void deleteByIdAndCatalog(UUID id, Catalog catalog);

    boolean existsByIdAndCatalog(UUID id, Catalog catalog);
}
