package com.catalogar.catalog;

import com.catalogar.company.CompanyDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record CatalogDto(
        UUID id,
        String slug,
        LocalDateTime publishedAt,
        boolean isPublished,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Optional<CompanyDto> company
) {
}
