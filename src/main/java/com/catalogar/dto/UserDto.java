package com.catalogar.dto;

import com.catalogar.model.Catalog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email,
        List<Catalog> catalogs,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
