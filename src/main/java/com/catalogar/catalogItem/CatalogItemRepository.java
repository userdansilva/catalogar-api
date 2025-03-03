package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {
    boolean existsByReferenceAndCatalog(Long reference, Catalog catalog);
}
