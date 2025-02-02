package com.catalogar.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDto(
        @NotEmpty(message = "E-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido. Formato válido: nome@exemplo.com")
        String email,
        @NotEmpty(message = "Senha é obrigatório")
        String password
) {
}
