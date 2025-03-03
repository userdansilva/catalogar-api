package com.catalogar.catalogItem;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record CatalogItemRequest(
        @NotEmpty(message = "Título do item de catálogo é obrigatório")
        String title,

        BigDecimal price,

        @NotEmpty(message = "Tipo de produto é obrigatório")
        String productId,

        List<String> categoryIds,

        boolean isDisabled
) {
}
