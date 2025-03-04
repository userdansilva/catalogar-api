package com.catalogar.catalogItem;

import com.catalogar.category.CategoryDto;
import com.catalogar.product.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CatalogItemDto(
        UUID id,
        String title,
        BigDecimal price,
        Long reference,
        ProductDto product,
        List<CategoryDto> categories,
        boolean isDisabled,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
