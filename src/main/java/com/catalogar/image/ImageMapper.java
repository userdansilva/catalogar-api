package com.catalogar.image;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ApiResponse<ImageSasTokenDto> toApiResponse(ImageSasToken imageSasToken) {
        ImageSasTokenDto imageSasTokenDto = new ImageSasTokenDto(
                imageSasToken.sasToken(),
                imageSasToken.name(),
                imageSasToken.url()
        );

        return new ApiResponse<>(imageSasTokenDto);
    }

    public ImageDto toDto(Image image) {
        return new ImageDto(
            image.getId(),
            image.getName(),
            image.getUrl(),
            image.getPosition(),
            image.getCreatedAt()
        );
    }
}
