package com.catalogar.repository;

import com.catalogar.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CatalogRepository
        extends JpaRepository<Catalog, UUID> {
    boolean existsBySlug(String slug);
}
