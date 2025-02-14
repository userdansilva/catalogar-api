package com.catalogar.company;

import java.time.LocalDateTime;

public record CompanyDto(
        String name,
        String mainSiteUrl,
        String phoneNumber,
        String businessTypeDescription,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
