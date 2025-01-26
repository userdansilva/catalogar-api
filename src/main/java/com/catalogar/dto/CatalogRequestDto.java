package com.catalogar.dto;

import com.catalogar.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record CatalogRequestDto(
        @NotEmpty(message = "Slug do catálogo é obrigatório")
        @Slug(message = "Formato inválido para slug. Formatos válidos: canecas-premium, formaturify, bolos-da-julia-123")
        String slug,

        boolean isActive
){
}
