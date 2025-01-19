package com.catalogar.category;

import org.springframework.data.domain.Sort;

public record CategoryFilter(
        Sort.Direction direction,
        String field
) {
}
