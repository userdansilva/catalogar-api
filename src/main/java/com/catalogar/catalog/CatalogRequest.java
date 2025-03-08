package com.catalogar.catalog;

import com.catalogar.common.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record CatalogRequest(
        @NotEmpty(message = "Nome do catálogo é obrigatório")
        String name,

        @NotEmpty(message = "Slug do catálogo é obrigatório")
        @Slug(message = "Formato inválido para slug. Use apenas letras, hífens(-) e/ou números. Ex.: exemplo, meu-exemplo, meu-exemplo-123")
        String slug,

        boolean isPublished
) {
}
