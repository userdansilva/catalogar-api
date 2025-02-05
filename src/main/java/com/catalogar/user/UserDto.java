package com.catalogar.user;

import com.catalogar.catalog.CatalogDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        List<CatalogDto> catalogs,
        CatalogDto currentCatalog,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
