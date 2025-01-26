package com.catalogar.dto;

import jakarta.validation.constraints.NotEmpty;

public record CompanyRequestDto(
        @NotEmpty(message = "Nome da empresa é obrigatório")
        String name,

        // Create and add validation
        String siteUrl,

        String phoneNumber,

        String logoUrl
) {
}
