package com.catalogar.image;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageDto(
        UUID id,
        String url,
        Short order,
        LocalDateTime createdAt
) {
}
