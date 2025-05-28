package com.catalogar.image;

import com.catalogar.common.validation.UuidImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ImageRequest(
        @NotEmpty(message = "Nome da imagem é obrigatório")
        @UuidImage(message = "Nome e/ou tipo de imagem inválida")
        String fileName,

        @Min(value = 1, message = "Posição da imagem deve ser maior ou igual a 1")
        int position
) {
}
