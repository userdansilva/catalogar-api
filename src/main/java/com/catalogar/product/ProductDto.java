package com.catalogar.product;

import java.time.LocalDateTime;

public record ProductDto(
        String name,
        String slug,
        boolean isActive,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
