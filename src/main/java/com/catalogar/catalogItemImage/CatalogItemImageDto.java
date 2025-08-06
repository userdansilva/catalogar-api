package com.catalogar.catalogItemImage;

import java.time.LocalDateTime;
import java.util.UUID;

public record CatalogItemImageDto(
        UUID id,
        String fileName,
        String url,
        Long sizeInBytes,
        Short width,
        Short height,
        String altText,
        Short position,
        LocalDateTime createdAt
) {
}
