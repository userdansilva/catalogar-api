package com.catalogar.category;

import com.catalogar.common.validation.Hexadecimal;
import com.catalogar.common.validation.Slug;
import jakarta.validation.constraints.NotEmpty;

public record CategoryRequestDto(
        @NotEmpty(message = "Nome da categoria é obrigatório")
        String name,
        @NotEmpty(message = "Slug da categoria é obrigatório")
        @Slug(message = "Formato inválido para slug. Formatos válidos: festa-junina, terceirao, exemplo-123")
        String slug,
        @NotEmpty(message = "Cor do texto é obrigatória")
        @Hexadecimal(message = "Cor do texto deve ser hexadecimal. Exemplo: #FFFFFF")
        String textColor,
        @NotEmpty(message = "Cor de fundo é obrigatória")
        @Hexadecimal(message = "Cor de fundo deve ser hexadecimal. Exemplo: #FD0054")
        String backgroundColor
) {
}
