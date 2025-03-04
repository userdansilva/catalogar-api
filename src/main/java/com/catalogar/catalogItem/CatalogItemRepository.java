package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {
    boolean existsByReferenceAndCatalog(Long reference, Catalog catalog);

    Page<CatalogItem> findAllByCatalog(Catalog catalog, Pageable pageable);

    Optional<CatalogItem> findByIdAndCatalog(UUID id, Catalog catalog);

    boolean existsByIdAndCatalog(UUID id, Catalog catalog);

    @Transactional
    void deleteByIdAndCatalog(UUID id, Catalog catalog);
}
