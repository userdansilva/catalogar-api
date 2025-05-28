package com.catalogar.storage;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    public ApiResponse<StorageDto> toApiResponse(Storage storage) {
        StorageDto storageDto = new StorageDto(
                storage.getFileName(),
                storage.getUploadUrl(),
                storage.getAccessUrl()
        );

        return new ApiResponse<>(storageDto);
    }
}
