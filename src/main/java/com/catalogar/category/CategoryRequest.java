package com.catalogar.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CategoryRequest(
        @NotEmpty(message = "campo obrigatório")
        String name,
        @NotEmpty(message = "campo obrigatório")
        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
                message = "deve ser hexadecimal")
        String color,
        @NotEmpty(message = "campo obrigatório")
        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
                message = "deve ser hexadecimal")
        String backgroundColor
) {
}
