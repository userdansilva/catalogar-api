package com.catalogar.catalogItem;

import com.catalogar.category.CategoryDto;
import com.catalogar.image.ImageDto;
import com.catalogar.productType.ProductTypeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CatalogItemDto(
        UUID id,
        String title,
        String caption,
        BigDecimal price,
        Long reference,
        ProductTypeDto productType,
        List<CategoryDto> categories,
        List<ImageDto> images,
        boolean isDisabled,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
