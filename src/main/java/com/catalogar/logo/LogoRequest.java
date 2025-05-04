package com.catalogar.logo;

import com.catalogar.common.validation.UuidImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record LogoRequest(
        @NotEmpty(message = "Nome da logo é obrigatória")
        @UuidImage(message = "Nome e/ou tipo de logo inválida")
        String name,

        @Min(value = 1, message = "Largura da imagem deve ser maior ou igual a 1")
        int width,

        @Min(value = 1, message = "Altura da imagem deve ser maior ou igual a 1")
        int height
) {
}
