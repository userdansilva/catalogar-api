package com.catalogar.productType;

import com.catalogar.common.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record ProductTypeRequest(
        @NotEmpty(message = "Nome do tipo de produto é obrigatório")
        String name,

        @NotEmpty(message = "Slug do tipo de produto é obrigatório")
        @Slug(message = "Formato inválido para slug. Formatos válidos: caneca, kit-formatura, exemplo-123")
        String slug,

        boolean isDisabled
) {
}
