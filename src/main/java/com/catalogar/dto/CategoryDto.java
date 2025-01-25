package com.catalogar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryDto(
        UUID id,
        String name,
        String slug,
        String textColor,
        String backgroundColor,
        boolean isActive,
        LocalDateTime disabledAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
