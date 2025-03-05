package com.catalogar.image;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ApiResponse<ImageSasTokenDto> toApiResponse(String sasToken) {
        ImageSasTokenDto imageSasTokenDto = new ImageSasTokenDto(sasToken);

        return new ApiResponse<>(imageSasTokenDto);
    }

    public ImageDto toDto(Image image) {
        return new ImageDto(
            image.getId(),
            image.getUrl(),
            image.getPosition(),
            image.getCreatedAt()
        );
    }
}
