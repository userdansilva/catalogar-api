package com.catalogar.catalogItemImage;

import com.catalogar.common.validation.UuidImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record CatalogItemImageRequest(
        @NotEmpty(message = "Nome da imagem é obrigatório")
        @UuidImage(message = "Nome e/ou tipo de imagem inválida")
        String fileName,

        @NotEmpty(message = "Url da imagem é obrigatório")
        String url,

        @Min(value = 1, message = "Tamanho da imagem deve ser maior ou igual a 1")
        int sizeInBytes,

        @Min(value = 1, message = "Largura da imagem deve ser maior ou igual a 1")
        int width,

        @Min(value = 1, message = "Altura da imagem deve ser maior ou igual a 1")
        int height,

        String altText,

        @Min(value = 1, message = "Posição da imagem deve ser maior ou igual a 1")
        int position
) {
}
