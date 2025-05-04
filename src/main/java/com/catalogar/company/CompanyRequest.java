package com.catalogar.company;

import com.catalogar.common.validation.Url;
import jakarta.validation.constraints.NotEmpty;

public record CompanyRequest(
    @NotEmpty(message = "Nome da empresa é obrigatório")
    String name,

    String description,

    @Url(message = "Formato inválido do site da empresa. " +
            "Ex.: https://exemplo.com.br ou https://www.exemplo.com.br")
    String mainSiteUrl,

    String phoneNumber,

    String businessTypeDescription
) {
}
