package com.catalogar.productType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductTypeDto(
        UUID id,
        String name,
        String slug,
        boolean isDisabled,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
