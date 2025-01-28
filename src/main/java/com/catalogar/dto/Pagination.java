package com.catalogar.dto;

public record Pagination(
        int currentPage,
        int perPage,
        int totalPages,
        int totalItems
) {
}
