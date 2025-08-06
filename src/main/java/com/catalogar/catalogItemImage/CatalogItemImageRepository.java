package com.catalogar.catalogItemImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatalogItemImageRepository extends JpaRepository<CatalogItemImage, UUID> {
}
