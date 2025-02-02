package com.catalogar.auth;

public record LoginDto(
        String token,
        Long expiresIn
) {
}
