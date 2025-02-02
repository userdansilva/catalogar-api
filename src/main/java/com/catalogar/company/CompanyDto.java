package com.catalogar.company;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyDto(
        UUID id,
        String name,
        String siteUrl,
        String logoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
