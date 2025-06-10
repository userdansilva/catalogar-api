package com.catalogar.catalog;

import com.catalogar.catalogItem.CatalogItemDto;
import com.catalogar.category.CategoryDto;
import com.catalogar.company.CompanyDto;
import com.catalogar.productType.ProductTypeDto;
import com.catalogar.theme.ThemeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PublishedCatalogDto(
        UUID id,
        String name,
        String slug,
        LocalDateTime publishedAt,
        boolean isPublished,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CompanyDto company,
        ThemeDto theme,
        List<CategoryDto> categories,
        List<ProductTypeDto> productTypes,
        List<CatalogItemDto> catalogItems
) {
}
