package com.catalogar.image;

public record ImageSasTokenDto(
        String sasToken,
        String name,
        String url
) {
}
