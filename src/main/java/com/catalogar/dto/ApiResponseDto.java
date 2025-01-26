package com.catalogar.dto;

public record ApiResponseDto<T>(
        T data,
        PaginationMetadataDto pagination
) {
}
