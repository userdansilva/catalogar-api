package com.catalogar.dto;

public record PaginationMetadataDto(
        int currentPage,
        int perPage,
        int totalPages,
        int totalItems
) {
}
