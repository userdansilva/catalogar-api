package com.catalogar.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(
        @NotEmpty(message = "Nome é obrigatório")
        String name,

        @NotEmpty(message = "Email do usuário é obrigatório")
        @Email(message = "Formato de email inválido. Formatos válidos: meu.exemplo@minhaempresa.com.br, exemplo@gmail.com")
        String email,

        @NotEmpty(message = "Senha é obrigatório")
        String password,

        String phoneNumber
) {
}
