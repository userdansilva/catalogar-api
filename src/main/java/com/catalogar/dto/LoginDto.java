package com.catalogar.dto;

public record LoginDto(
        String token,
        Long expiresIn
) {
}
