package com.catalogar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CatalogDto(
        UUID id,
        String slug,
        LocalDateTime publishedAt,
        boolean isPublished,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CompanyDto company
) {
}
