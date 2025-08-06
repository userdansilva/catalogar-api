package com.catalogar.image;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImageDto(
        UUID id,
        String fileName,
        String url,
        Long sizeInBytes,
        Short width,
        Short height,
        String altText,
        LocalDateTime createdAt
) {
}
