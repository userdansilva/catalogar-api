package com.catalogar.catalog;

import com.catalogar.common.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record CreateCatalogRequest(
        @NotEmpty(message = "Slug do catálogo é obrigatório")
        @Slug(message = "Formato inválido para slug. Formatos válidos: canecas-premium, formaturify, bolos-da-julia-123")
        String slug,

        boolean isActive
){
}
