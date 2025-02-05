package com.catalogar.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "E-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,
        @NotEmpty(message = "Senha é obrigatório")
        String password
) {
}
