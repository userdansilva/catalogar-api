package com.catalogar.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record CatalogDto(
        UUID id,
        UUID slug,
        UUID publishedAt,
        boolean isPublished,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        CompanyDto company
) {
}
