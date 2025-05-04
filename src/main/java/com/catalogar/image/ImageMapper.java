package com.catalogar.image;

import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
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
