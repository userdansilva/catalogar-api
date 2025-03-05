package com.catalogar.image;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    ApiResponse<ImageSasTokenDto> toApiResponse(String sasToken) {
        ImageSasTokenDto imageSasTokenDto = new ImageSasTokenDto(sasToken);

        return new ApiResponse<>(imageSasTokenDto);
    }
}
