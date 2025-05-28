package com.catalogar.logo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record LogoRequest(
        @NotEmpty(message = "Nome do arquivo da logo é obrigatório")
        String fileName,

        @NotEmpty(message = "Nome original do arquivo da logo é obrigatório")
        String originalFileName,

        @Min(value = 1, message = "Largura da logo deve ser maior ou igual a 1")
        int width,

        @Min(value = 1, message = "Altura da logo deve ser maior ou igual a 1")
        int height
) {
}
