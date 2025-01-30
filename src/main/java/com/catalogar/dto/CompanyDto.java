package com.catalogar.dto;

import java.util.UUID;

public record CompanyDto(
        UUID id,
        String name,
        String siteUrl,
        String logoUrl,
        String createdAt,
        String updatedAt
) {
}
