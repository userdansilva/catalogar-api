package com.catalogar.catalogItem;

import com.catalogar.image.ImageRequest;
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
        String productId,

        List<String> categoryIds,

        @NotEmpty(message = "Imagem do item de catálogo é obrigatória")
        @Valid
        List<ImageRequest> images,

        boolean isDisabled
) {
}
