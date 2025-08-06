package com.catalogar.image;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ImageRequest(
        @NotEmpty(message = "Nome da imagem é obrigatório")
        String fileName,

        @NotEmpty(message = "Url da imagem é obrigatório")
        String url,

        @Min(value = 1, message = "Tamanho da imagem deve ser maior ou igual a 1")
        int sizeInBytes,

        @Min(value = 1, message = "Largura da imagem deve ser maior ou igual a 1")
        int width,

        @Min(value = 1, message = "Altura da imagem deve ser maior ou igual a 1")
        int height,

        String altText
) {
}
