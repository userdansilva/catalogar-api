package com.catalogar.theme;

import com.catalogar.common.validation.Hexadecimal;
import com.catalogar.logo.LogoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record ThemeRequest(
        @NotEmpty(message = "Cor primária do tema é obrigatório")
        @Hexadecimal(message = "Cor primária do tema deve ser hexadecimal. Exemplo: #FFFFFF")
        String primaryColor,

        @NotEmpty(message = "Cor secundária do tema é obrigatório")
        @Hexadecimal(message = "Cor secundária do tema deve ser hexadecimal. Exemplo: #FFF000")
        String secondaryColor,

        @Valid
        LogoRequest logo
) {
}
