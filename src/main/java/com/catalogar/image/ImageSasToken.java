package com.catalogar.image;

public record ImageSasToken(
        String sasToken,
        String name,
        String url
) {
}
