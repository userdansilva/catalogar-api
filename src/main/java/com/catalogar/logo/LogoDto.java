package com.catalogar.logo;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogoDto(
        UUID id,
        String name,
        String url,
        Short width,
        Short height,
        LocalDateTime createdAt
) {
}
