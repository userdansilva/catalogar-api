package com.catalogar.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotEmpty(message = "Nome é obrigatório")
        String name,

        @NotEmpty(message = "Email do usuário é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotEmpty(message = "Senha é obrigatório")
        @Size(min = 8, max = 64, message = "Senha deve ter entre 8 e 64 caracteres")
        String password
) {
}
