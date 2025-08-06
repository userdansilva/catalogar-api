package com.catalogar.catalogItem;

import com.catalogar.catalogItemImage.CatalogItemImageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record CatalogItemRequest(
        @NotEmpty(message = "Título do item de catálogo é obrigatório")
        String title,

        String caption,

        BigDecimal price,

        @NotEmpty(message = "Tipo de produto é obrigatório")
        String productTypeId,

        List<String> categoryIds,

        @NotEmpty(message = "Imagem do item de catálogo é obrigatória")
        @Valid
        List<CatalogItemImageRequest> images,

        boolean isDisabled
) {
}
