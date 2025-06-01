package com.catalogar.productType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductTypeFilter(
        @Pattern(
                regexp = "asc|desc",
                message = "Ordenação inválida. Valores permitidos: crescente, decrescente"
        )
        String order,

        @Pattern(
                regexp = "name|createdAt",
                message = "Campo de ordenação inválido. Valores permitidos: nome, data de criação"
        )
        String field,

        @Positive(
                message = "Página deve ser maior ou igual a 1"
        )
        String page,

        @Positive(
                message = "Quantidade de itens por página deve ser maior ou igual a 1"
        )
        @Max(
                value = 20,
                message = "Quantidade máxima de itens por página deve ser menor ou igual a 20"
        )
        String perPage
) {
}
