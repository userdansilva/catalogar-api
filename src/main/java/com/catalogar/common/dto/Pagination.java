package com.catalogar.common.dto;

public record Pagination(
        int currentPage,
        int perPage,
        int totalPages,
        int totalItems
) {
}
