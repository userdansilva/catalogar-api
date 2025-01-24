package com.catalogar.dto;

import jakarta.validation.constraints.Pattern;

public record CategoryFilterDto (
        @Pattern(
                regexp = "asc|desc",
                message = "Ordenação inválida. Valores permitidos: crescente, decrescente"
        )
        String order,
        @Pattern(
                regexp = "name|createdAt",
                message = "Campo de ordenação inválido. Valores permitidos: nome, data de criação"
        )
        String field
) {
}

