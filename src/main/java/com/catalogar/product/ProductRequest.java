package com.catalogar.product;

import com.catalogar.common.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record ProductRequest(
        @NotEmpty(message = "Nome do produto é obrigatório")
        String name,

        @NotEmpty(message = "Slug do produto é obrigatório")
        @Slug(message = "Formato inválido para slug. Formatos válidos: caneca, kit-formatura, exemplo-123")
        String slug,

        boolean isDisabled
) {
}
