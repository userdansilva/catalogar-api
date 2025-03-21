package com.catalogar.category;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDto(
        UUID id,
        String name,
        String slug,
        String textColor,
        String backgroundColor,
        boolean isDisabled,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
