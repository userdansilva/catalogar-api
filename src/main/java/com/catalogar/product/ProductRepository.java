package com.catalogar.product;

import com.catalogar.catalog.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, UUID> {

    boolean existsByNameAndCatalog(String name, Catalog catalog);

    boolean existsBySlugAndCatalog(String slug, Catalog catalog);

    Page<Product> findAllByCatalog(Catalog catalog, Pageable pageable);
}
