package com.catalogar.catalog;

import jakarta.validation.constraints.NotEmpty;

public record CreateCatalogRequest(
        @NotEmpty(message = "Nome do catálogo é obrigatório")
        String name
) {
}
