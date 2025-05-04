package com.catalogar.storage;

public record StorageDto(
        String sasToken,
        String name,
        String url
) {
}
