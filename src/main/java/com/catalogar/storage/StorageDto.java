package com.catalogar.storage;

public record StorageDto(
        String fileName,
        String uploadUrl,
        String accessUrl
) {
}
