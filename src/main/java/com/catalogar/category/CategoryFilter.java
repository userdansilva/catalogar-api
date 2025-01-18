package com.catalogar.category;

import jakarta.validation.constraints.Pattern;

public record CategoryFilter(
        @Pattern(
                regexp = "ASC|DESC",
                message = "Ordenação inválida. Valores permitidos: ASC, DESC"
        )
        String order,
        @Pattern(
                regexp = "name|createdAt",
                message = "Campo de ordenação inválido. Valores permitidos: name, createdAt"
        )
        String field
) {
}
