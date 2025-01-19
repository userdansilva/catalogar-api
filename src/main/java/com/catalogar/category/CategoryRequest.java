package com.catalogar.category;

import com.catalogar.validation.Hexadecimal;
import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(
        @NotEmpty(message = "Nome da categoria é obrigatório")
        String name,
        @NotEmpty(message = "Cor do texto é obrigatória")
        @Hexadecimal(message = "Cor do texto deve ser hexadecimal. Exemplo: #FFFFFF")
        String color,
        @NotEmpty(message = "Cor de fundo é obrigatória")
        @Hexadecimal(message = "Cor de fundo deve ser hexadecimal. Exemplo: #FD0054")
        String backgroundColor
) {
}
