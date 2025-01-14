package com.catalogar.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record NewCategoryRequest(
        @NotEmpty String name,
        @NotEmpty @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$") String color,
        @NotEmpty @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$") String backgroundColor
) {
}
