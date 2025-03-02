package com.catalogar.product;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        String slug,
        boolean isDisabled,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
