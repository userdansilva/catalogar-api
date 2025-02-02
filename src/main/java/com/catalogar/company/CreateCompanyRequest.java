package com.catalogar.company;

import jakarta.validation.constraints.NotEmpty;

public record CreateCompanyRequest(
        @NotEmpty(message = "Nome da empresa é obrigatório")
        String name,

        // Create and add validation
        String siteUrl,

        String phoneNumber,

        String logoUrl
) {
}
