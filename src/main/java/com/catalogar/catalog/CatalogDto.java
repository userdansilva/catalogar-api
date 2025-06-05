package com.catalogar.catalog;

import com.catalogar.company.CompanyDto;
import com.catalogar.theme.ThemeDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record CatalogDto(
        UUID id,
        String name,
        Optional<String> slug,
        Optional<LocalDateTime> publishedAt,
        boolean isPublished,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Optional<CompanyDto> company,
        Optional<ThemeDto> theme
) {
}
