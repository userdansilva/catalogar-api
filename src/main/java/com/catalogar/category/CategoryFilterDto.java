package com.catalogar.category;

import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Sort;

public class CategoryFilterDto {
    @Pattern(
            regexp = "asc|desc",
            message = "Ordenação inválida. Valores permitidos: crescente, decrescente"
    )
    private final String order;
    @Pattern(
            regexp = "name|createdAt",
            message = "Campo de ordenação inválido. Valores permitidos: nome, data de criação"
    )
    private final String field;

    public CategoryFilterDto(String order, String field) {
        this.order = order;
        this.field = field;
    }

    public CategoryFilter toCategoryFilter() {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                order.toUpperCase());

        return new CategoryFilter(
                direction,
                field
        );
    }
}
